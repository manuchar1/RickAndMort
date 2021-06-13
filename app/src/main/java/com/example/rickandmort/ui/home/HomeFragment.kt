package com.example.rickandmort.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmort.R
import com.example.rickandmort.adapter.CharactersAdapter
import com.example.rickandmort.databinding.FragmentHomeBinding
import com.example.rickandmort.main.MainActivity
import com.example.rickandmort.main.MainViewModel
import com.example.rickandmort.usecases.Resourcee
import com.example.rickandmort.usecases.Constants.QUERY_PAGE_SIZE

class HomeFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var charactersAdapterr: CharactersAdapter

    private var binding: FragmentHomeBinding? = null


    val TAG = "HomeFragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding?.recyclerView?.showShimmer()
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()
        charactersAdapterr.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("character", it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundle

            )

        }

        viewModel.characters.observe(viewLifecycleOwner, { response ->

            when (response) {
                is Resourcee.Success -> {
                    binding?.recyclerView?.hideShimmer()
                    response.data?.let { characterResponse ->
                        charactersAdapterr.differ.submitList(characterResponse.results.toList())
                        val totalPages = characterResponse.info.count / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.charactersPage == totalPages
                        if (isLastPage) {
                            binding?.recyclerView?.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resourcee.Error -> {
                    binding?.recyclerView?.hideShimmer()
                    response.message?.let { message ->
                        Log.e(TAG, "an error accured:$message ")

                    }
                }
                is Resourcee.Loading -> {
                    binding?.recyclerView?.showShimmer()

                }

            }


        })

    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisbleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisbleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisbleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem
                    && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getCharacters()
                isScrolling = false
            }

        }
    }

    private fun setupRecyclerView() {
        charactersAdapterr = CharactersAdapter()
        binding?.recyclerView?.apply {
            adapter = charactersAdapterr
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HomeFragment.scrollListener)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
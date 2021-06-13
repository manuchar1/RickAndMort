package com.example.rickandmort.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmort.R
import com.example.rickandmort.adapter.CharactersAdapter
import com.example.rickandmort.databinding.FragmentSearchBinding
import com.example.rickandmort.main.MainActivity
import com.example.rickandmort.main.MainViewModel
import com.example.rickandmort.usecases.Resourcee
import com.example.rickandmort.utils.Constants
import com.example.rickandmort.utils.Constants.SEARCH_TIME_DELAY
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var charactersAdapterr: CharactersAdapter

    lateinit var viewModel: MainViewModel
    val TAG = "SearchFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        init()
    }

    private fun init() {
        charactersAdapterr.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("character",it)
            }
            findNavController().navigate(
                R.id.action_searchFragment2_to_detailsFragment,
                bundle

            )

        }

        var job : Job? = null
        binding.etSearchInput.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchCharacter(editable.toString())
                    }
                }

            }
        }
        viewModel.searchCharacters.observe(viewLifecycleOwner) { response ->

            when (response) {
                is Resourcee.Success -> {
                    hideProgressBar()
                    response.data?.let { characterResponse ->
                        charactersAdapterr.differ.submitList(characterResponse.results.toList())
                        val totalPages = characterResponse.info.count / Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.searchCharactersPage == totalPages
                        if (isLastPage) {
                            binding.rvSearchCharacter.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resourcee.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "an error accured:$message ")

                    }
                }
                is Resourcee.Loading -> {
                    showProgressBar()
                }

            }


        }


    }

    private fun hideProgressBar() {
        binding?.progressBar2?.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding?.progressBar2?.visibility = View.VISIBLE
        isLoading = true

    }



    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL ) {
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
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem
                    && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.searchCharacter(binding.etSearchInput.text.toString())
                isScrolling = false
            }

        }
    }



    private fun setupRecyclerView() {
        charactersAdapterr = CharactersAdapter()
        binding?.rvSearchCharacter?.apply {
            adapter = charactersAdapterr
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchFragment.scrollListener)
        }


    }

}
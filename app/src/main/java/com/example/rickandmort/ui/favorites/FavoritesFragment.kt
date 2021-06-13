package com.example.rickandmort.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmort.R
import com.example.rickandmort.adapter.CharactersAdapter
import com.example.rickandmort.databinding.FragmentFavoritesBinding
import com.example.rickandmort.main.MainActivity
import com.example.rickandmort.main.MainViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    lateinit var viewModel: MainViewModel

    lateinit var charactersAdapterr: CharactersAdapter

    val TAG = "Favorites"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
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
                 R.id.action_favoritesFragment2_to_detailsFragment,
                 bundle

             )

         }

         val itemTouchHelperCallback = object  : ItemTouchHelper.SimpleCallback(
             ItemTouchHelper.UP or ItemTouchHelper.DOWN,
             ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

         ){
             override fun onMove(
                 recyclerView: RecyclerView,
                 viewHolder: RecyclerView.ViewHolder,
                 target: RecyclerView.ViewHolder
             ): Boolean {
                 return true
             }

             override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                 val posotion = viewHolder.adapterPosition
                 val character = charactersAdapterr.differ.currentList[posotion]
                 viewModel.deleteCharacter(character)
                 view?.let {
                     Snackbar.make(it,"successfully deleted character", Snackbar.LENGTH_LONG).apply {
                         setAction("Undo"){
                             viewModel.saveCharacter(character)

                         }
                         show()
                     }
                 }

             }

         }

         ItemTouchHelper(itemTouchHelperCallback).apply {
             attachToRecyclerView(binding.rvFavorites)
         }

         viewModel.getSavedCharacter().observe(viewLifecycleOwner, Observer { characters ->
             charactersAdapterr.differ.submitList(characters)
         })
     }

    private fun setupRecyclerView() {
        charactersAdapterr = CharactersAdapter()
        binding?.rvFavorites?.apply {
            adapter = charactersAdapterr
            layoutManager = LinearLayoutManager(activity)
        }


    }

}
package com.example.rickandmort.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.rickandmort.databinding.FragmentDetailsBinding
import com.example.rickandmort.main.MainActivity
import com.example.rickandmort.main.MainViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    lateinit var viewModel: MainViewModel

    val args: DetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val character = args.character

        binding.tvNamed.text = character.name

        binding.fbSaveCharacter.setOnClickListener {
            viewModel.saveCharacter(character)
            Snackbar.make(view,"Character saved successfully",Snackbar.LENGTH_SHORT).show()
        }
    }




}
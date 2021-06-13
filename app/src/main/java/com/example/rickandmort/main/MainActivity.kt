package com.example.rickandmort.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmort.databinding.ActivityMainBinding
import com.example.rickandmort.ui.ViewModelProviderFactory
import com.example.rickandmorty.db.CharactersDatabase
import com.example.rickandmort.repository.Repository

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = Repository(CharactersDatabase(this))
        val viewModelProviderFactory = ViewModelProviderFactory(application, newsRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)
    }
}
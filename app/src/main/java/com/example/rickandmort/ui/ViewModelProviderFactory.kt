package com.example.rickandmort.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmort.main.MainViewModel
import com.example.rickandmort.repository.Repository


class ViewModelProviderFactory(
    val app: Application,
    private val repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(app, repository) as T
    }
}
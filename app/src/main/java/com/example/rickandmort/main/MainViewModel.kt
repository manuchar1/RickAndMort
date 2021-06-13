package com.example.rickandmort.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmort.CharactersApplication
import com.example.rickandmort.data.models.Character
import com.example.rickandmort.usecases.Resourcee
import com.example.rickandmort.data.models.CharacterResponse
import com.example.rickandmort.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel(app: Application, val repository: Repository) : AndroidViewModel(app) {


    val characters: MutableLiveData<Resourcee<CharacterResponse>> = MutableLiveData()
    var charactersPage = 1
    var characterResponse: CharacterResponse? = null

    val searchCharacters: MutableLiveData<Resourcee<CharacterResponse>> = MutableLiveData()
    var searchCharactersPage = 1

    var searchResponse: CharacterResponse? = null



    init {
        getCharacters()

    }

    fun getCharacters() = viewModelScope.launch {
        safeCharactersCall()

    }


    fun searchCharacter(searchQuery: String) = viewModelScope.launch {
        safeSearchCall(searchQuery)
    }



    private fun handleSearchCharactersResponse(response: Response<CharacterResponse>) : Resourcee<CharacterResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchCharactersPage++
                if (searchResponse == null) {
                    searchResponse = resultResponse
                } else {
                    val oldCharacters = searchResponse?.results
                    val newCharacters = resultResponse.results
                    oldCharacters?.addAll(newCharacters)

                }
                return Resourcee.Success(searchResponse ?: resultResponse)
            }
        }
        return Resourcee.Error(response.message())

    }

    private fun handleCharactersResponse(response: Response<CharacterResponse>) : Resourcee<CharacterResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                charactersPage++
                if (characterResponse == null) {
                    characterResponse = resultResponse
                } else {
                    val oldCharacters = characterResponse?.results
                    val newCharacters = resultResponse.results
                    oldCharacters?.addAll(newCharacters)

                }
                return Resourcee.Success(characterResponse ?: resultResponse)
            }
        }
        return Resourcee.Error(response.message())
        //return Resource.error(response.message())
    }

    fun saveCharacter(character: Character) = viewModelScope.launch {
        repository.upsert(character)
    }

    fun getSavedCharacter() = repository.getSavedCharacter()

    fun deleteCharacter(character: Character) = viewModelScope.launch {
        repository.deleteCharacter(character)
    }


    private suspend fun safeSearchCall(searchQuery: String) {
        searchCharacters.postValue(Resourcee.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.searchCharacter(searchQuery,searchCharactersPage)
                searchCharacters.postValue(handleSearchCharactersResponse(response))
            } else {
                searchCharacters.postValue(Resourcee.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchCharacters.postValue(Resourcee.Error("Network Failure"))
                else -> searchCharacters.postValue(Resourcee.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeCharactersCall() {
        characters.postValue(Resourcee.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getCharacters(charactersPage)
                characters.postValue(handleCharactersResponse(response))
            } else {
                characters.postValue(Resourcee.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> characters.postValue(Resourcee.Error("Network Failure"))
                else -> characters.postValue(Resourcee.Error("Conversion Error"))
            }
        }
    }



    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<CharactersApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }




}
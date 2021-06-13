package com.example.rickandmort.data.network



import com.example.rickandmort.data.models.CharacterResponse
import com.example.rickandmort.usecases.Constants.API_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersAPI {

    @GET(API_ENDPOINT)
    suspend fun getCharacters(
        @Query("page")
        pageNumber: Int = 1
    ): Response<CharacterResponse>


    @GET(API_ENDPOINT)
    suspend fun searchForCharacters(
        @Query("name")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1
    ): Response<CharacterResponse>
}
package com.example.rickandmort.data.models


import androidx.annotation.Keep

@Keep
data class CharacterResponse(
    val info: Info,
    val results: MutableList<Character>
)
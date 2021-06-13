package com.example.rickandmort.data.models


import androidx.annotation.Keep

@Keep
data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any?
)
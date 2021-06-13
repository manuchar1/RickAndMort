package com.example.rickandmort.data.models


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Keep
@Entity(tableName = "characters")
data class Character(
    val created: String,
   // val episode: List<String>,
    val gender: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
):Serializable
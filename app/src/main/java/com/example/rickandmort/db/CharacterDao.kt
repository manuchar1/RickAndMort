package com.example.rickandmorty.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rickandmort.data.models.Character


@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(characters: Character): Long

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): LiveData<List<Character>>

    @Delete
    suspend fun deleteCharacter(character: Character)


}
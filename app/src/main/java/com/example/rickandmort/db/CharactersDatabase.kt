package com.example.rickandmorty.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmort.data.models.Character

@Database(
    entities = [Character::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class CharactersDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao

    companion object {
        @Volatile
        private var instance: CharactersDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CharactersDatabase::class.java,
                "character_db.db"
            ).build()
    }
}
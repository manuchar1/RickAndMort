package com.example.rickandmorty.db

import androidx.room.TypeConverter
import com.example.rickandmort.data.models.Location
import com.example.rickandmort.data.models.Origin

class Converters {

    @TypeConverter
    fun fromSource(origin: Origin): String {
        return origin.name
    }

    @TypeConverter
    fun toOrigin(name: String): Origin {
        return Origin(name,name)
    }

    @TypeConverter
    fun fromLocation(location: Location): String {
        return location.name
    }

    @TypeConverter
    fun toLocation(name: String): Location {
        return Location(name,name)
    }


}
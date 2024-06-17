package com.funapps.themovie.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.funapps.themovie.data.model.KnowFor

class Converters {

    @TypeConverter
    fun fromKnowForList(value: List<KnowFor>?): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toKnowForList(value: String): List<KnowFor>? {
        val gson = Gson()
        val listType = object : TypeToken<List<KnowFor>>() {}.type
        return gson.fromJson(value, listType)
    }
}
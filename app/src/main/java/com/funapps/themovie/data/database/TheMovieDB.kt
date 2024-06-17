package com.funapps.themovie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.funapps.themovie.data.model.Movie
import com.funapps.themovie.data.model.Popular

@Database(entities = [Popular::class, Movie::class], version = 1)
@TypeConverters(Converters::class)
abstract class TheMovieDB: RoomDatabase() {
    abstract fun popularDao(): PopularDao
    abstract fun moviesDao(): MovieDao
}
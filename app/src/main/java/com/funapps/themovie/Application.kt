package com.funapps.themovie

import android.app.Application
import androidx.room.Room
import com.funapps.themovie.data.database.TheMovieDB
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {

    companion object {
        lateinit var database: TheMovieDB
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            TheMovieDB::class.java, "TheMovie-database"
        ).build()
    }

}
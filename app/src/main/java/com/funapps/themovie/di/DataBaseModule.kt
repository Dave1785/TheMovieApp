package com.funapps.themovie.di

import android.content.Context
import androidx.room.Room
import com.funapps.themovie.data.database.MovieDao
import com.funapps.themovie.data.database.PopularDao
import com.funapps.themovie.data.database.TheMovieDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TheMovieDB {
        return Room.databaseBuilder(context, TheMovieDB::class.java, "the_movie_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePopularDao(database: TheMovieDB): PopularDao {
        return database.popularDao()
    }

    @Provides
    fun provideMoviesDao(database: TheMovieDB): MovieDao {
        return database.moviesDao()
    }
}
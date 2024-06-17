package com.funapps.themovie.di

import com.funapps.themovie.data.repository.MoviesRepository
import com.funapps.themovie.data.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMoviesRepository(popularRepository: MoviesRepositoryImpl): MoviesRepository
}
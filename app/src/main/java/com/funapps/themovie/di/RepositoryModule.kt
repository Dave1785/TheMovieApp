package com.funapps.themovie.di

import com.funapps.themovie.data.repository.MoviesRepository
import com.funapps.themovie.data.repository.MoviesRepositoryImpl
import com.funapps.themovie.data.repository.PopularRepository
import com.funapps.themovie.data.repository.PopularRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providePopularRepository(popularRepository: PopularRepositoryImpl): PopularRepository

    @Binds
    abstract fun provideMoviesRepository(popularRepository: MoviesRepositoryImpl): MoviesRepository
}
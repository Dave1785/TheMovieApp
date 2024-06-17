package com.funapps.themovie.data.repository

import com.funapps.themovie.data.database.MovieDao
import com.funapps.themovie.network.NetworkState
import com.funapps.themovie.network.SortedByType
import com.funapps.themovie.network.performGetOperationMovies
import com.funapps.themovie.network.remoteSource.MoviesRemoteSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val moviesRemoteSource: MoviesRemoteSource,
    private val movieDao: MovieDao,
    private val networkState: NetworkState,
) {

    suspend fun getMoviesList(
        page: Int,
        sortedByType: SortedByType
    ) = performGetOperationMovies(
        networkState = networkState,
        databaseQuery = { movieDao.getAllMovies() },
        networkCall = { moviesRemoteSource.getMoviesList(page, sortedByType) },
        saveCallResult = { it?.let { movieList -> movieDao.insertAll(movieList) } },
    )

}
package com.funapps.themovie.data.repository

import com.funapps.themovie.data.model.MovieResponse
import com.funapps.themovie.data.model.PopularResponse
import com.funapps.themovie.network.ResultType
import com.funapps.themovie.network.SortedByType
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getMoviesList(page: Int, sortedByType: SortedByType): Flow<ResultType<MovieResponse>>
}
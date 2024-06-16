package com.funapps.themovie.data.repository

import com.funapps.themovie.data.model.PopularResponse
import com.funapps.themovie.network.ResultType
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getMoviesList(page: Int): Flow<ResultType<PopularResponse>>
}
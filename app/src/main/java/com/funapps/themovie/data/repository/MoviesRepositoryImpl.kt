package com.funapps.themovie.data.repository

import com.funapps.themovie.data.model.PopularResponse
import com.funapps.themovie.data.remote.TheMovieApiService
import com.funapps.themovie.network.ResultType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val service: TheMovieApiService) :
    MoviesRepository {
    override suspend fun getMoviesList(page: Int): Flow<ResultType<PopularResponse>> = flow {
        try {
            val response = service.getMovieList(
                include_adult = false,
                include_video = false,
                language = "en-US",
                sort_by = "popularity.desc",
                page = page.toString()
            )
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(ResultType.Success(data))
                } else {
                    emit(ResultType.Error(Exception("Empty response")))
                }
            } else {
                emit(ResultType.Error(Exception("Network call failed")))
            }
        } catch (e: Exception) {
            emit(ResultType.Error(e))
        }
    }
}
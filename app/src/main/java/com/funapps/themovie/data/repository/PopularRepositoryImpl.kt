package com.funapps.themovie.data.repository

import com.funapps.themovie.data.model.Popular
import com.funapps.themovie.data.model.PopularResponse
import com.funapps.themovie.data.remote.TheMovieApiService
import com.funapps.themovie.network.ResultType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PopularRepositoryImpl  @Inject constructor(private val service: TheMovieApiService): PopularRepository {

    override suspend fun getPopularList(page: Int): Flow<ResultType<PopularResponse>> = flow {
        try {
            val response = service.getPopularList("en-US", page.toString())
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
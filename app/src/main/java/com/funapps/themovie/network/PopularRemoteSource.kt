package com.funapps.themovie.network

import com.funapps.themovie.data.model.PopularResponse
import com.funapps.themovie.data.remote.TheMovieApiService
import retrofit2.Response
import javax.inject.Inject

class PopularRemoteSource @Inject constructor(private val service: TheMovieApiService): BaseDataSource() {

    suspend fun getMostPopularListAsync(): Response<PopularResponse?> {
       return service.getPopularListAsync(language = "en-US", page = "1")
    }

}
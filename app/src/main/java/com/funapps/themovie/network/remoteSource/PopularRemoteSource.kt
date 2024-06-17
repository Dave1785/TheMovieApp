package com.funapps.themovie.network.remoteSource

import com.funapps.themovie.data.model.PopularResponse
import com.funapps.themovie.data.remote.TheMovieApiService
import retrofit2.Response
import javax.inject.Inject

class PopularRemoteSource @Inject constructor(private val service: TheMovieApiService) {

    suspend fun getMostPopularList(): Response<PopularResponse?> {
        return service.getPopularList(language = "en-US", page = "1")
    }

}
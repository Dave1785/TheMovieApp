package com.funapps.themovie.data.remote

import com.funapps.themovie.data.model.Popular
import com.funapps.themovie.data.model.PopularResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieApiService {

    @GET("person/popular?")
    suspend fun getPopularList(@Query("language") language: String, @Query("page") page: String): Response<PopularResponse>

}
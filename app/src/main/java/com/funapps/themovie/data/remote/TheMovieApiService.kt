package com.funapps.themovie.data.remote

import com.funapps.themovie.data.model.MovieResponse
import com.funapps.themovie.data.model.Popular
import com.funapps.themovie.data.model.PopularResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieApiService {

    @GET("person/popular?")
    suspend fun getPopularList(
        @Query("language") language: String,
        @Query("page") page: String
    ): Response<PopularResponse>

    @GET("discover/movie?")
    suspend fun getMovieList(
        @Query("include_adult") include_adult: Boolean,
        @Query("include_video") include_video: Boolean,
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("page") page: String
    ): Response<MovieResponse>
}
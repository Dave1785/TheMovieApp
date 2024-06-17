package com.funapps.themovie.network.remoteSource

import com.funapps.themovie.data.model.MovieResponse
import com.funapps.themovie.data.remote.TheMovieApiService
import com.funapps.themovie.network.SortedByType
import retrofit2.Response
import javax.inject.Inject

class MoviesRemoteSource @Inject constructor(private val service: TheMovieApiService) {

    suspend fun getMoviesList(page: Int, sortedByType: SortedByType): Response<MovieResponse> {
        return service.getMovieList(
            language = "en-US",
            page = page.toString(),
            include_video = false,
            include_adult = false,
            sort_by = sortedByType.value
        )
    }

}
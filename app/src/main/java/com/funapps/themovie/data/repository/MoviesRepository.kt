package com.funapps.themovie.data.repository

import com.funapps.themovie.data.model.Movie
import com.funapps.themovie.network.SortedByType

interface MoviesRepository {

    suspend fun getMoviesList(
        page: Int,
        sortedByType: SortedByType
    ): List<Movie>?
}
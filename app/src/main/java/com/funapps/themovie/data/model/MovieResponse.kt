package com.funapps.themovie.data.model

data class MovieResponse(
    val page: Int,
    val results: List<Movie>
)
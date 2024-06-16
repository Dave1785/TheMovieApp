package com.funapps.themovie.data.model

data class ResponseMovie(
    val page: Int,
    val results: List<Movie>
)
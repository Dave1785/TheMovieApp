package com.funapps.themovie.data.model

data class Movie (
    val adult: Boolean,
    val id: Double,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteAccount: Int
)
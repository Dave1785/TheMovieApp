package com.funapps.themovie.data.model

import com.google.gson.annotations.SerializedName

data class Movie (
    val adult: Boolean,
    val id: Double,
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Float,
    val voteAccount: Int
)
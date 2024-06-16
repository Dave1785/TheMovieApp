package com.funapps.themovie.data.model

data class PopularResponse(
    val page: Int,
    val results: List<Popular>
)
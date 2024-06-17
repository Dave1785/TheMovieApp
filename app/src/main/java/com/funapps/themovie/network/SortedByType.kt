package com.funapps.themovie.network

enum class SortedByType(val value: String) {
    MOST_POPULAR("popularity.desc"),
    MOST_RANKED("vote_count.desc"),
    MOST_RECOMMENDED("vote_average.desc"),
}
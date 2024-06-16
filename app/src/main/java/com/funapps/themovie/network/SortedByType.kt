package com.funapps.themovie.network

enum class SortedByType(val value: String) {
    MOST_POPULAR("popularity.desc"),
    MOST_RANKED("vote_count.desc"),
    MOST_RECOMENDED("vote_average.desc"),
}
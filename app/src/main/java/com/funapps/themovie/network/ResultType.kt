package com.funapps.themovie.network

sealed class ResultType<out T> {
    data class Success<out T>(val data: T) : ResultType<T>()
    data class Error(val exception: Throwable?) : ResultType<Nothing>()

}

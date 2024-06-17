package com.funapps.themovie.network

import kotlinx.coroutines.Deferred

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Deferred<T>): Resource<T> {
        return try {
            Resource.success(call().await())
        } catch (e: Exception) {
            return Resource.error("fail")
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a following reason: $message")
    }

}
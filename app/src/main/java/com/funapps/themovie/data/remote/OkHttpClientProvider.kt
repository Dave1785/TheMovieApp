package com.funapps.themovie.data.remote

import okhttp3.OkHttpClient

object OkHttpClientProvider {
    fun create(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxODdhNDVlNzU1NWY1N2ZkOTNlMzJhMjliYTc0ZGIxNyIsInN1YiI6IjY2NmUyNWYwNWU1YTg4NmVkNDQ3YjA5NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.AwDfw0jXTOp4i9RjoqNIKp5FiKwel4Ghs-7hTc45-0Q")
                    .build()
                chain.proceed(request)
            }
            .build()
    }
}
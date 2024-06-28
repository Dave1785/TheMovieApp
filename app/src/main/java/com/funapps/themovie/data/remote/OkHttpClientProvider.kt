package com.funapps.themovie.data.remote

import okhttp3.OkHttpClient

const val token =
    "ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKaGRXUWlPaUl4T0RkaE5EVmxOelUxTldZMU4yWmtPVE5sTXpKaE1qbGlZVGMwWkdJeE55SXNJbk4xWWlJNklqWTJObVV5TldZd05XVTFZVGc0Tm1Wa05EUTNZakE1TnlJc0luTmpiM0JsY3lJNld5SmhjR2xmY21WaFpDSmRMQ0oyWlhKemFXOXVJam94ZlEuQXdEZncwalhUT3A0aTlSam9xTklLcDVGaUt3ZWw0R2hzLTdoVGM0NS0wUQ=="

object OkHttpClientProvider {
    fun create(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer ${Base64.decodeInBase64(token)}")
                    .build()
                chain.proceed(request)
            }
            .build()
    }
}
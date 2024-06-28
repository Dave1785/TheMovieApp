package com.funapps.themovie.data.remote

import java.util.Base64

object Base64 {

    fun decodeInBase64(value: String): String {
        val decodedBytes = Base64.getDecoder().decode(value)
        return String(decodedBytes)
    }
}
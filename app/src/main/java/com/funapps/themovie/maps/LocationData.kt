package com.funapps.themovie.maps

import java.text.SimpleDateFormat
import java.util.Date


class LocationData(val name: String, val latitude: Double, val longitude: Double, timestamp: Date) {
    private val timestamp: Date

    init {
        this.timestamp = timestamp
    }

    fun getTimestamp(): Date {
        return timestamp
    }

    val formattedDate: String
        get() {
            val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm")
            return sdf.format(timestamp)
        }
}


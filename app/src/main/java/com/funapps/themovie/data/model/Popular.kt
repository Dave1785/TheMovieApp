package com.funapps.themovie.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Popular(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val name: String,
    val originalName: String,
    val popularity: Double
): Parcelable
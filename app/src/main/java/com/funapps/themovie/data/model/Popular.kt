package com.funapps.themovie.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Popular(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    @SerializedName("profile_path")
    val profilePath: String,
    val name: String,
    @SerializedName("known_for_department")
    val knowForDepartment: String,
    @SerializedName("known_for")
    val knowFor: List<KnowFor>,
    val originalName: String,
    val popularity: Double
): Parcelable

@Parcelize
data class KnowFor(
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_count")
    val voteCount: Int
): Parcelable
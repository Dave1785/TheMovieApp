package com.funapps.themovie.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.funapps.themovie.data.database.Converters
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "most_popular")
data class Popular(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val gender: Int,
    @SerializedName("profile_path")
    val profilePath: String,
    val name: String,
    @SerializedName("known_for_department")
    val knowForDepartment: String,
    @SerializedName("known_for")
    @TypeConverters(Converters::class)
    val knowFor: List<KnowFor>,
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
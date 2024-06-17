package com.funapps.themovie.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.funapps.themovie.data.model.Popular

@Dao
interface PopularDao {
    @Query("SELECT * FROM most_popular")
    fun getAllPopular(): List<Popular>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(popular: Popular)
}
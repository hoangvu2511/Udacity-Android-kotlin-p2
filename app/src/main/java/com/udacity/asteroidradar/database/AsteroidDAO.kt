package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.utils.TimeUtils

@Dao
interface AsteroidDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Asteroid>)

    @Query("SELECT * FROM asteroid WHERE closeApproachDate = :today")
    suspend fun loadToday(today: String = TimeUtils.getTodayFormat()): List<Asteroid>


    @Query("SELECT * FROM asteroid ORDER BY closeApproachDate DESC")
    suspend fun loadAllSaved(): List<Asteroid>

    @Query("SELECT * FROM asteroid WHERE timeInMilli BETWEEN :start AND :end ORDER BY timeInMilli")
    suspend fun loadWeek(start: Long, end: Long): List<Asteroid>

}
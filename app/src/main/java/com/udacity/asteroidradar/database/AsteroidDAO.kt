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

    @Query("SELECT * FROM asteroid WHERE closeApproachDate = :today ORDER BY closeApproachDate DESC")
    fun loadAll(today: String = TimeUtils.getTodayFormat()): LiveData<List<Asteroid>>
}
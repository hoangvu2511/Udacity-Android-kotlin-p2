package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.model.Asteroid

@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDAO

    companion object {
        var INSTANCE: AsteroidDatabase? = null
            private set

        fun initInstance(context: Context) {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AsteroidDatabase::class.java,
                        "asteroid_database"
                    )
                        .build()
                }
            }
        }
    }
}
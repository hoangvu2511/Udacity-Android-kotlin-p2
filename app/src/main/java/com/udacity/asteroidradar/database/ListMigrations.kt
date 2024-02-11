package com.udacity.asteroidradar.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object ListMigrations {
    val Migration_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Asteroid ADD COLUMN timeInMilli INTEGER NOT NULL DEFAULT 0")
        }

    }

    val list = arrayOf(Migration_1_2)
}
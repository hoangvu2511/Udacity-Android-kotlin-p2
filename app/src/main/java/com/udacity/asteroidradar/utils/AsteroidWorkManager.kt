package com.udacity.asteroidradar.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.NetworkUtils
import com.udacity.asteroidradar.database.AsteroidDAO
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AsteroidWorkManager(appContext: Context, workerParameters: WorkerParameters) :
    Worker(appContext, workerParameters) {
    private val workerScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun doWork(): Result {
        workerScope.launch {
            val date = TimeUtils.getTodayFormat()
            val result = NetworkUtils.loadNewFeed(date, date)
            val listAsteroid = result.parseAsteroidsJsonResult()
            launch(Dispatchers.Default) {
                if (AsteroidDatabase.INSTANCE == null) {
                    AsteroidDatabase.initInstance(applicationContext)
                }
                AsteroidDatabase.INSTANCE?.asteroidDao()?.insertAll(listAsteroid)
            }
        }
        return Result.success()
    }

}
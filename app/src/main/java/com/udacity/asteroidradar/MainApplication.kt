package com.udacity.asteroidradar

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.utils.AsteroidWorkManager
import java.util.concurrent.TimeUnit

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AsteroidDatabase.initInstance(this)
        initWorker()
    }

    private fun initWorker() {
        val request =
            PeriodicWorkRequestBuilder<AsteroidWorkManager>(1, TimeUnit.DAYS).setConstraints(
                Constraints.Builder()
                    .setRequiresCharging(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "Sync new asteroid",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }
}
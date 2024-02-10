package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.NetworkUtils
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.ImageOfDay
import com.udacity.asteroidradar.utils.TimeUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun initData() {
        loadNewFeed()
        loadImageOfDay()
    }

    private val networkCatcher = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }


    private val _listAsteroid = MutableLiveData<List<Asteroid>>()
    val listAsteroid: LiveData<List<Asteroid>> = _listAsteroid

    fun loadNewFeed(isWeek: Boolean = false) {
        viewModelScope.launch(networkCatcher) {
            val startDate = TimeUtils.getTodayFormat()
            val endDate = if (isWeek) TimeUtils.getPrev7Day() else startDate
            val result = NetworkUtils.loadNewFeed(startDate, endDate)
            val listAsteroid = result.parseAsteroidsJsonResult()
            async(Dispatchers.Default) {
                AsteroidDatabase.INSTANCE?.asteroidDao()?.insertAll(listAsteroid)
            }
        }
    }


    private val _imageOfDay = MutableLiveData<ImageOfDay>()
    val imageOfDay: LiveData<ImageOfDay> = _imageOfDay

    private fun loadImageOfDay() {
        viewModelScope.launch(networkCatcher) {
            val result = NetworkUtils.loadImageOfDay()
            _imageOfDay.value = result
        }
    }
}
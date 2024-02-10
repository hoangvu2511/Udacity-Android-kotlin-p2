package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.NetworkUtils
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.ImageOfDay
import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.utils.TimeUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainViewModel : ViewModel() {

    fun initData() {
        loadNewFeed()
        loadImageOfDay()
        loadToday()
    }

    private val networkCatcher = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }


    private val _listAsteroid = MutableLiveData<List<Asteroid>>()
    val listAsteroid: LiveData<List<Asteroid>> = _listAsteroid

    private fun loadNewFeed() {
        viewModelScope.launch(networkCatcher) {
            val startDate = TimeUtils.getTodayFormat()
            val endDate = TimeUtils.getNext7Day()
            val result = NetworkUtils.loadNewFeed(startDate, endDate)
            val listAsteroid = result.parseAsteroidsJsonResult()
            async(Dispatchers.Default) {
                AsteroidDatabase.INSTANCE?.asteroidDao()?.insertAll(listAsteroid)
                if (_listAsteroid.value.isNullOrEmpty()) {
                    loadToday()
                }
            }
        }
    }

    fun loadToday() {
        viewModelScope.launch(networkCatcher) {
            val todayAsteroid =
                AsteroidDatabase.INSTANCE?.asteroidDao()?.loadToday() ?: return@launch
            _listAsteroid.value = todayAsteroid
        }
    }

    fun loadAllSaved() {
        viewModelScope.launch(networkCatcher) {
            val todayAsteroid =
                AsteroidDatabase.INSTANCE?.asteroidDao()?.loadAllSaved() ?: return@launch
            _listAsteroid.value = todayAsteroid
        }
    }

    fun loadWeek() {
        viewModelScope.launch(networkCatcher) {
            val todayFormat = TimeUtils.getTodayFormat()
            val date = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
                .parse(todayFormat)
            val start = date.time
            val end = start + 7 * 24 * 60 * 60 * 1000
            val todayAsteroid =
                AsteroidDatabase.INSTANCE?.asteroidDao()?.loadWeek(start, end) ?: return@launch
            _listAsteroid.value = todayAsteroid
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
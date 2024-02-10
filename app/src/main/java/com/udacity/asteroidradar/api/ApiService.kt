package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.model.ImageOfDay
import com.udacity.asteroidradar.model.NewFeed
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("neo/rest/v1/feed?")
    suspend fun getNewFeed(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY
    ): NewFeed


    @GET("planetary/apod")
    suspend fun imageOfDay(@Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY): ImageOfDay
}
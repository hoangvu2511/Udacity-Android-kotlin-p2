package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkUtils {
    private var retrofitInstance: Retrofit? = null

    private fun generateRetrofit(): Retrofit {
        if (retrofitInstance == null) {
            val client = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                client.addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                })
            }
            val moshi = Moshi.Builder()
                .add(JSONObjectAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()
            retrofitInstance = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client.build())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }
        return retrofitInstance!!
    }

    private var service: ApiService? = null
    private fun getService(): ApiService {
        return synchronized(this) {
            if (service == null) {
                service = generateRetrofit().create(ApiService::class.java)
            }
            service!!
        }
    }

    suspend fun loadNewFeed(startDate: String, endDate: String) =
        getService().getNewFeed(startDate, endDate)

    suspend fun loadImageOfDay() = getService().imageOfDay()

}

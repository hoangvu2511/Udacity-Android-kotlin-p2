package com.udacity.asteroidradar.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TimeUtils {

    fun getTodayFormat(): String {
        val date = Calendar.getInstance()
        val formatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.ENGLISH)
        return formatter.format(date.time)
    }

    fun getPrev7Day(): String {
        val date = Calendar.getInstance()
        date.set(Calendar.WEEK_OF_YEAR, date.get(Calendar.WEEK_OF_YEAR) - 1)
        val formatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.ENGLISH)
        return formatter.format(date.time)
    }

    fun getNext7Day(): String {
        val date = Calendar.getInstance()
        date.set(Calendar.WEEK_OF_YEAR, date.get(Calendar.WEEK_OF_YEAR) + 1)
        val formatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.ENGLISH)
        return formatter.format(date.time)
    }
}
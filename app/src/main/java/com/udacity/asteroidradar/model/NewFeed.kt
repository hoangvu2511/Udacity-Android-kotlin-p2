package com.udacity.asteroidradar.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.utils.Constants
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//@JsonClass(generateAdapter = true)
data class NewFeed(
    @Json(name = "element_count")
    val elementCount: Int? = null,
    val links: Links? = null,
    @Json(name = "near_earth_objects")
    val nearEarthObjectsJson: JSONObject? = null
) {
    fun parseAsteroidsJsonResult(): ArrayList<Asteroid> {
        nearEarthObjectsJson ?: return ArrayList()
        val asteroidList = ArrayList<Asteroid>()
        val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()
        for (formattedDate in nextSevenDaysFormattedDates) {
            val nearObj = nearEarthObjectsJson
            if (nearObj.has(formattedDate)) {
                val dateAsteroidJsonArray = nearObj.getJSONArray(formattedDate)

                for (i in 0 until dateAsteroidJsonArray.length()) {
                    val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
                    val id = asteroidJson.getLong("id")
                    val codename = asteroidJson.getString("name")
                    val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
                    val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                        .getJSONObject("kilometers").getDouble("estimated_diameter_max")

                    val closeApproachData = asteroidJson
                        .getJSONArray("close_approach_data").getJSONObject(0)
                    val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                        .getDouble("kilometers_per_second")
                    val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                        .getDouble("astronomical")
                    val isPotentiallyHazardous = asteroidJson
                        .getBoolean("is_potentially_hazardous_asteroid")

                    val asteroid = Asteroid(
                        id,
                        codename,
                        formattedDate,
                        absoluteMagnitude,
                        estimatedDiameter,
                        relativeVelocity,
                        distanceFromEarth,
                        isPotentiallyHazardous
                    )
                    asteroidList.add(asteroid)
                }
            }
        }

        return asteroidList
    }

    private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
        val formattedDateList = ArrayList<String>()

        val calendar = Calendar.getInstance()
        for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
            val currentTime = calendar.time
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            formattedDateList.add(dateFormat.format(currentTime))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return formattedDateList
    }
}

@JsonClass(generateAdapter = true)
data class Links(
    val next: String? = null,
    val previous: String? = null,
    val self: String? = null
) {
    fun toJsonObject(): JSONObject {
        return JSONObject().apply {
            put("next", next)
            put("previous", previous)
            put("self", self)
        }
    }

    companion object {
        fun JSONObject.toLinks(): Links {
            return Links(
                optString("next"),
                optString("previous"),
                optString("self"),
            )
        }
    }
}

//data class NearEarthObject(
//    val absolute_magnitude_h: Double?,
//    val close_approach_data: List<CloseApproachData>?,
//    val estimated_diameter: EstimatedDiameter?,
//    val id: String?,
//    val is_potentially_hazardous_asteroid: Boolean?,
//    val is_sentry_object: Boolean?,
//    val links: Links?,
//    val name: String?,
//    val nasa_jpl_url: String?,
//    val neo_reference_id: String?
//)
//
//data class CloseApproachData(
//    val close_approach_date: String?,
//    val close_approach_date_full: String?,
//    val epoch_date_close_approach: Long?,
//    val miss_distance: MissDistance?,
//    val orbiting_body: String?,
//    val relative_velocity: RelativeVelocity?
//)
//
//data class EstimatedDiameter(
//    val feet: Feet?, val kilometers: Kilometers?, val meters: Meters?, val miles: Miles?
//)
//
//data class MissDistance(
//    val astronomical: String?, val kilometers: String?, val lunar: String?, val miles: String?
//)
//
//data class RelativeVelocity(
//    val kilometers_per_hour: String?,
//    val kilometers_per_second: String?,
//    val miles_per_hour: String?
//)
//
//data class Feet(
//    val estimated_diameter_max: Double?, val estimated_diameter_min: Double?
//)
//
//data class Kilometers(
//    val estimated_diameter_max: Double?, val estimated_diameter_min: Double?
//)
//
//data class Meters(
//    val estimated_diameter_max: Double?, val estimated_diameter_min: Double?
//)
//
//data class Miles(
//    val estimated_diameter_max: Double?, val estimated_diameter_min: Double?
//)

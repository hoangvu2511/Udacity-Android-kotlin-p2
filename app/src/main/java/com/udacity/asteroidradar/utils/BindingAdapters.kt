package com.udacity.asteroidradar.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.udacity.asteroidradar.R

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val str = if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        R.string.not_safe_asteroid
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        R.string.safe_asteroid
    }
    imageView.contentDescription = imageView.context.getString(str)
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val str = if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        R.string.not_safe_asteroid
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        R.string.safe_asteroid
    }
    imageView.contentDescription = imageView.context.getString(str)
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

package com.udacity.asteroidradar.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidItemHolderBinding
import com.udacity.asteroidradar.model.Asteroid

class AsteroidViewHolder(private val binding: AsteroidItemHolderBinding): RecyclerView.ViewHolder(binding.root) {
    fun initView(asteroid: Asteroid, onItemClick: (Asteroid) -> Unit) {
        binding.asteroid = asteroid
        binding.root.setOnClickListener {
            onItemClick(asteroid)
        }
    }
}
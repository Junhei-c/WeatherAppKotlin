package com.example.android.appdeweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.appdeweather.mapper.WeatherUiMapper
import com.example.android.appdeweather.repository.WeatherRepository

class WeatherViewModelFactory(
    private val repository: WeatherRepository,
    private val mapper: WeatherUiMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository, mapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

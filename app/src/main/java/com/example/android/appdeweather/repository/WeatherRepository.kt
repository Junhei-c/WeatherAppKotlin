package com.example.android.appdeweather.repository

import com.example.android.appdeweather.network.RetrofitInstance

class WeatherRepository {
    suspend fun fetchWeather() = RetrofitInstance.api.getWeather()
}

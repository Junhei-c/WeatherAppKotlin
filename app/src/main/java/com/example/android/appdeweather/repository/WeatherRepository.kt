package com.example.android.appdeweather.repository

import com.example.android.appdeweather.network.RetrofitInstance

class WeatherRepository {
    suspend fun fetchWeather(date: String) =
        RetrofitInstance.api.getWeather(date = date).body()
}


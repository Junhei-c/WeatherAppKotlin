package com.example.android.appdeweather.network

import com.example.android.appdeweather.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface WeatherApiService {
    @GET("real-time/api/weather?api=wbgt")
    suspend fun getWeather(): Response<ApiResponse>
}


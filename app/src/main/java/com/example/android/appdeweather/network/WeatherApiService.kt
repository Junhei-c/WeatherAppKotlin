package com.example.android.appdeweather.network

import com.example.android.appdeweather.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("real-time/api/weather")
    suspend fun getWeather(
        @Query("api") api: String = "wbgt",
        @Query("date") date: String
    ): Response<ApiResponse>
}


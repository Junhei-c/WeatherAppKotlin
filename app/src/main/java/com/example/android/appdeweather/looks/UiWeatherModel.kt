package com.example.android.appdeweather.looks

data class UiWeatherModel(
    val name: String,
    val townCenter: String,
    val temperature: String,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val heatStress: String,
    val date: String
)

package com.example.android.appdeweather.model

data class ApiResponse(
    val data: WeatherData
)

data class WeatherData(
    val records: List<WeatherRecord>
)

data class WeatherRecord(
    val datetime: String,
    val item: WeatherItem
)

data class WeatherItem(
    val readings: List<Reading>
)

data class Reading(
    val station: Station,
    val location: Location,
    val wbgt: String,
    val heatStress: String
)

data class Station(
    val id: String,
    val name: String,
    val townCenter: String
)

data class Location(
    val latitude: String,
    val longitude: String
)




package com.example.android.appdeweather.model

data class ApiResponse(
    val data: DataWrapper
)

data class DataWrapper(
    val records: List<Record>
)

data class Record(
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
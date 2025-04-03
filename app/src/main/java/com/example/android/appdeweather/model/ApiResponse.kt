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



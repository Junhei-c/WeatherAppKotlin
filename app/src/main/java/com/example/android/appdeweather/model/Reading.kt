package com.example.android.appdeweather.model

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


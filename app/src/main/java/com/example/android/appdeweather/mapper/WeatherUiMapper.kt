package com.example.android.appdeweather.mapper

import com.example.android.appdeweather.looks.UiWeatherModel
import com.example.android.appdeweather.model.Reading

class WeatherUiMapper {
    fun mapToUi(reading: Reading, datetime: String): UiWeatherModel {
        return UiWeatherModel(
            name = reading.station.name,
            townCenter = reading.station.townCenter,
            temperature = reading.wbgt,
            id = reading.station.id,
            latitude = reading.location.latitude.toDoubleOrNull() ?: 0.0,
            longitude = reading.location.longitude.toDoubleOrNull() ?: 0.0,
            heatStress = reading.heatStress,
            date = datetime
        )
    }
}
package com.example.android.appdeweather.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.android.appdeweather.looks.UiWeatherModel
import com.example.android.appdeweather.repository.WeatherRepository
import com.example.android.appdeweather.mapper.WeatherUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val uiMapper: WeatherUiMapper
) : ViewModel() {

    private val _weatherUi = MutableLiveData<List<UiWeatherModel>>()
    val weatherUi: LiveData<List<UiWeatherModel>> = _weatherUi

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            try {
                val start = System.currentTimeMillis()
                val response = repository.fetchWeather()
                val end = System.currentTimeMillis()
                Log.d("WeatherFetch", "Loaded in ${end - start} ms")

                val items = response?.items ?: emptyList()

                if (items.isNotEmpty()) {
                    val record = items.first()
                    val readings = record.readings
                    val datetime = record.datetime

                    val mappedList = readings.map { reading ->
                        uiMapper.mapToUi(reading, datetime)
                    }

                    _weatherUi.postValue(mappedList)
                    _error.postValue(null)
                } else {
                    _error.postValue("No weather data available")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown Error")
                Log.e("WeatherFetch", "Error: ", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}






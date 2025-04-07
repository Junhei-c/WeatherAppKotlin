package com.example.android.appdeweather.viewmodel

import androidx.lifecycle.*
import com.example.android.appdeweather.looks.UiWeatherModel
import com.example.android.appdeweather.mapper.WeatherUiMapper
import com.example.android.appdeweather.repository.WeatherRepository
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
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.fetchWeather()
                val record = response?.data?.records?.firstOrNull()

                val readings = record?.item?.readings ?: emptyList()
                val datetime = record?.datetime ?: ""

                val mappedList = readings.map { reading ->
                    uiMapper.mapToUi(reading, datetime)
                }

                _weatherUi.value = mappedList
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
            _isLoading.value = false
        }
    }
}






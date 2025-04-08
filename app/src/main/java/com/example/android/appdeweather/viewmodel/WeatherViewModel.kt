package com.example.android.appdeweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.appdeweather.looks.UiWeatherModel
import com.example.android.appdeweather.mapper.WeatherUiMapper
import com.example.android.appdeweather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val uiMapper: WeatherUiMapper
) : ViewModel() {

    private val _weatherUi = MutableLiveData<List<UiWeatherModel>>()
    val weatherUi: LiveData<List<UiWeatherModel>> = _weatherUi

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> = _selectedDate


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchWeather(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            try {
                val response = repository.fetchWeather(date)
                val record = response?.data?.records?.firstOrNull()
                val readings = record?.item?.readings ?: emptyList()
                val datetime = record?.datetime ?: ""

                val mappedList = readings.map {
                    uiMapper.mapToUi(it, datetime)
                }

                _weatherUi.postValue(mappedList)
                _error.postValue(null)
            } catch (e: Exception) {
                _error.postValue("Error: ${e.localizedMessage}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun updateSelectedDate(date: String) {
        _selectedDate.value = date
        fetchWeather(date)
    }
}







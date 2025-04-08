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

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> = _selectedDate

    private val _weatherUi = MutableLiveData<List<UiWeatherModel>>()
    val weatherUi: LiveData<List<UiWeatherModel>> = _weatherUi

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun updateSelectedDate(date: String) {
        _selectedDate.value = date
        filterByDate(date)
    }

    private fun filterByDate(date: String) {
        val originalList = _weatherUi.value ?: return
        val filteredList = originalList.filter { it.date.startsWith(date) }
        _weatherUi.postValue(filteredList)
    }

    fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            try {
                val response = repository.fetchWeather()

                if (response?.data?.records?.isNotEmpty() == true) {
                    val record = response.data.records.first()
                    val datetime = record.datetime
                    val readings = record.item.readings

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
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}







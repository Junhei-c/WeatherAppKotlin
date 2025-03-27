package com.example.android.appdeweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.appdeweather.model.Reading
import com.example.android.appdeweather.repository.WeatherRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _readings = MutableStateFlow<List<Reading>>(emptyList())
    val readings: StateFlow<List<Reading>> = _readings

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    val filteredReadings: StateFlow<List<Reading>> = combine(_readings, _searchQuery) { readings, query ->
        if (query.isBlank()) {
            readings
        } else {
            readings.filter {
                it.station.name.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }


    fun getWeather() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.fetchWeather()
                val record = response.data.records.firstOrNull()
                _readings.value = record?.item?.readings ?: emptyList()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
            _isLoading.value = false
        }
    }
}


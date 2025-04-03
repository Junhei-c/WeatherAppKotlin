package com.example.android.appdeweather.viewmodel

import androidx.lifecycle.*
import com.example.android.appdeweather.looks.UiWeatherModel
import com.example.android.appdeweather.repository.WeatherRepository
import com.example.android.appdeweather.mapper.WeatherUiMapper
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

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> = _selectedDate

    private val _filteredWeatherUi = MediatorLiveData<List<UiWeatherModel>>()
    val filteredWeatherUi: LiveData<List<UiWeatherModel>> = _filteredWeatherUi

    init {
        _filteredWeatherUi.addSource(_weatherUi) { updateFilter() }
        _filteredWeatherUi.addSource(_searchQuery) { updateFilter() }
        _filteredWeatherUi.addSource(_selectedDate) { updateFilter() }
    }

    private fun updateFilter() {
        val original = _weatherUi.value ?: emptyList()
        val date = _selectedDate.value
        val query = _searchQuery.value ?: ""

        var filtered = original

        if (!date.isNullOrBlank()) {
            filtered = filtered.filter { it.date.contains(date) }
        }

        if (query.isNotBlank()) {
            filtered = filtered.filter { it.name.contains(query, ignoreCase = true) }
        }

        _filteredWeatherUi.value = filtered
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun fetchWeather() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.fetchWeather()
                val record = response.data.records.firstOrNull()
                val readings = record?.item?.readings ?: emptyList()
                val datetime = record?.datetime ?: ""

                val mappedList = readings.map { reading ->
                    uiMapper.mapToUi(reading, datetime)
                }

                _weatherUi.value = mappedList
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown Error"
            }
            _isLoading.value = false
        }
    }
}




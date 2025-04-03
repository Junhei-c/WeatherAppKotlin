package com.example.android.appdeweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.appdeweather.databinding.ActivityMainBinding
import com.example.android.appdeweather.utils.WeatherObservers
import com.example.android.appdeweather.utils.WeatherDatePicker
import com.example.android.appdeweather.mapper.WeatherUiMapper
import com.example.android.appdeweather.repository.WeatherRepository
import com.example.android.appdeweather.looks.WeatherAdapter
import com.example.android.appdeweather.viewmodel.WeatherViewModel
import com.example.android.appdeweather.viewmodel.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        WeatherObservers.observe(this, binding, viewModel, adapter)
        WeatherDatePicker.setup(this, binding, viewModel)

        viewModel.fetchWeather()
    }

    private fun setupRecyclerView() {
        adapter = WeatherAdapter(emptyList())
        binding.weatherRecycler.layoutManager = LinearLayoutManager(this)
        binding.weatherRecycler.adapter = adapter
    }

    private fun setupViewModel() {
        val factory = WeatherViewModelFactory(WeatherRepository(), WeatherUiMapper())
        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]
    }
}

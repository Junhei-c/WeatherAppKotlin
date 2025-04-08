package com.example.android.appdeweather

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.appdeweather.databinding.ActivityMainBinding
import com.example.android.appdeweather.looks.UiWeatherModel
import com.example.android.appdeweather.looks.WeatherAdapter
import com.example.android.appdeweather.mapper.WeatherUiMapper
import com.example.android.appdeweather.repository.WeatherRepository
import com.example.android.appdeweather.utils.NetworkUtils
import com.example.android.appdeweather.utils.WeatherDatePicker
import com.example.android.appdeweather.utils.WeatherObservers
import com.example.android.appdeweather.viewmodel.WeatherViewModel
import com.example.android.appdeweather.viewmodel.WeatherViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var adapter: WeatherAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView()
        WeatherObservers.observe(this, binding, viewModel, adapter)
        WeatherDatePicker.setup(this, binding, viewModel)

        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        binding.dateField.setText(today)

        if (NetworkUtils.isConnected(this)) {
            viewModel.fetchWeather(today)
        } else {
            showNetworkError()
        }
    }

    private fun setupRecyclerView() {
        adapter = WeatherAdapter(emptyList()) { weatherItem ->
            openDetailActivity(weatherItem)
        }
        binding.weatherRecycler.layoutManager = LinearLayoutManager(this)
        binding.weatherRecycler.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            val selectedDate = binding.dateField.text.toString()
            if (NetworkUtils.isConnected(this)) {
                viewModel.fetchWeather(selectedDate)
            } else {
                showNetworkError()
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupViewModel() {
        val factory = WeatherViewModelFactory(
            WeatherRepository(),
            WeatherUiMapper()
        )
        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]
    }

    private fun openDetailActivity(weatherItem: UiWeatherModel) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("name", weatherItem.name)
            putExtra("townCenter", weatherItem.townCenter)
            putExtra("temperature", weatherItem.temperature)
            putExtra("id", weatherItem.id)
            putExtra("latitude", weatherItem.latitude)
            putExtra("longitude", weatherItem.longitude)
            putExtra("heatStress", weatherItem.heatStress)
            putExtra("datetime", weatherItem.date)
        }
        startActivity(intent)
    }

    private fun showNetworkError() {
        binding.errorText.visibility = View.VISIBLE
        binding.errorText.text = "No Internet Connection"
    }
}


package com.example.android.appdeweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.appdeweather.databinding.ActivityMainBinding
import com.example.android.appdeweather.utils.WeatherObservers
import com.example.android.appdeweather.mapper.WeatherUiMapper
import com.example.android.appdeweather.repository.WeatherRepository
import com.example.android.appdeweather.looks.WeatherAdapter
import com.example.android.appdeweather.viewmodel.WeatherViewModel
import com.example.android.appdeweather.viewmodel.WeatherViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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


        CoroutineScope(Dispatchers.IO).launch {
            viewModel.fetchWeather()
            withContext(Dispatchers.Main) {
                WeatherObservers.observe(this@MainActivity, binding, viewModel, adapter)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = WeatherAdapter(emptyList())
        binding.weatherRecycler.layoutManager = LinearLayoutManager(this)
        binding.weatherRecycler.adapter = adapter
    }

    private fun setupViewModel() {
        val factory = WeatherViewModelFactory(
            WeatherRepository(),
            WeatherUiMapper()
        )
        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]
    }
}

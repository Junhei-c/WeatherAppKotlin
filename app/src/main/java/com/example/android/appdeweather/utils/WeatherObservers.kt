package com.example.android.appdeweather.utils

import androidx.lifecycle.LifecycleOwner
import com.example.android.appdeweather.databinding.ActivityMainBinding
import com.example.android.appdeweather.viewmodel.WeatherViewModel
import com.example.android.appdeweather.looks.WeatherAdapter

object WeatherObservers {
    fun observe(
        owner: LifecycleOwner,
        binding: ActivityMainBinding,
        viewModel: WeatherViewModel,
        adapter: WeatherAdapter
    ) {
        viewModel.weatherUi.observe(owner) { adapter.updateData(it) }

        viewModel.isLoading.observe(owner) {
            binding.progressBar.visibility = if (it) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.error.observe(owner) {
            binding.errorText.visibility = if (it != null) android.view.View.VISIBLE else android.view.View.GONE
            binding.errorText.text = it ?: ""
        }

        viewModel.filteredWeatherUi.observe(owner) { adapter.updateData(it) }
    }
}


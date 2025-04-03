package com.example.android.appdeweather.utils

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.android.appdeweather.databinding.ActivityMainBinding
import com.example.android.appdeweather.looks.WeatherAdapter
import com.example.android.appdeweather.viewmodel.WeatherViewModel

object WeatherObservers {
    fun observe(
        owner: LifecycleOwner,
        binding: ActivityMainBinding,
        viewModel: WeatherViewModel,
        adapter: WeatherAdapter
    ) {

        viewModel.filteredWeatherUi.observe(owner) { list ->
            adapter.updateData(list)
        }


        viewModel.isLoading.observe(owner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


        viewModel.error.observe(owner) { errorMsg ->
            binding.errorText.text = errorMsg ?: ""
            binding.errorText.visibility = if (errorMsg != null) View.VISIBLE else View.GONE
        }

        viewModel.weatherUi.observe(owner) { adapter.updateData(it) }
    }
}



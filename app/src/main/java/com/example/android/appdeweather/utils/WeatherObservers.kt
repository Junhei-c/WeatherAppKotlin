package com.example.android.appdeweather.utils

import androidx.appcompat.app.AppCompatActivity
import com.example.android.appdeweather.databinding.ActivityMainBinding
import com.example.android.appdeweather.looks.WeatherAdapter
import com.example.android.appdeweather.viewmodel.WeatherViewModel

object WeatherObservers {
    fun observe(
        activity: AppCompatActivity,
        binding: ActivityMainBinding,
        viewModel: WeatherViewModel,
        adapter: WeatherAdapter
    ) {
        viewModel.weatherUi.observe(activity) { data ->
            adapter.updateData(data)
        }

        viewModel.isLoading.observe(activity) { loading ->
            binding.progressBar.visibility = if (loading) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.error.observe(activity) { errorMsg ->
            binding.errorText.visibility = if (errorMsg != null) android.view.View.VISIBLE else android.view.View.GONE
            binding.errorText.text = errorMsg ?: ""
        }
    }
}




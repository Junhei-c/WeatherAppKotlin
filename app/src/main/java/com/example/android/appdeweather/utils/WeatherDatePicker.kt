package com.example.android.appdeweather.utils

import android.app.DatePickerDialog
import android.content.Context
import com.example.android.appdeweather.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

object WeatherDatePicker {
    fun setup(context: Context, binding: ActivityMainBinding) {

        val now = Calendar.getInstance().time
        val formattedNow = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(now)

        binding.dateField.setText(formattedNow)

        binding.dateField.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(context,
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }
                    val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)
                    binding.dateField.setText(formattedDate)

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }
}



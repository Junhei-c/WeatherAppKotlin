package com.example.android.appdeweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.appdeweather.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val townCenter = intent.getStringExtra("townCenter")
        val temperature = intent.getStringExtra("temperature")
        val id = intent.getStringExtra("id")
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val heatStress = intent.getStringExtra("heatStress")


        binding.nameText.text = "Name: $name"
        binding.townText.text = "Town: $townCenter"
        binding.tempText.text = "Temperature: $temperature°C"
        binding.idText.text = "Station ID: $id"
        binding.geoText.text = "Location: ($latitude, $longitude)"
        binding.heatStressText.text = "Heat Stress Level: $heatStress"
    }
}

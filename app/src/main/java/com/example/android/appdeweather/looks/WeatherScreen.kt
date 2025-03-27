@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.android.appdeweather.looks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.appdeweather.model.Reading
import com.example.android.appdeweather.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val filtered by viewModel.filteredReadings.collectAsState()
    val search by viewModel.searchQuery.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getWeather()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // ✅ Updated label
        OutlinedTextField(
            value = search,
            onValueChange = { viewModel.updateSearchQuery(it) },
            label = { Text("Search by station name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            isLoading -> CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            error != null -> Text("Error: $error", color = MaterialTheme.colorScheme.error)
            filtered.isEmpty() -> Text("No matching stations found.")
            else -> WeatherList(filtered)
        }
    }
}

@Composable
fun WeatherList(readings: List<Reading>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(readings) { reading ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = reading.station.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Town: ${reading.station.townCenter}")
                    Text(text = "Temp: ${reading.wbgt}°C")
                }
            }
        }
    }
}

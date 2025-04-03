package com.example.android.appdeweather.looks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.appdeweather.R
import com.example.android.appdeweather.looks.UiWeatherModel

class WeatherAdapter(private var list: List<UiWeatherModel>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val town: TextView = itemView.findViewById(R.id.town)
        val temp: TextView = itemView.findViewById(R.id.temp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.town.text = "Town: ${item.townCenter}"
        holder.temp.text = "Temp: ${item.temperature}°C"
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<UiWeatherModel>) {
        list = newList
        notifyDataSetChanged()
    }
}
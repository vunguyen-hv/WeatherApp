package com.plcoding.weatherapp.presentation

import android.location.Location
import com.plcoding.weatherapp.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val location: Location? = null
)

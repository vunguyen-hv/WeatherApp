package com.plcoding.weatherapp.data.network

import com.squareup.moshi.Json

data class WeatherDto (
    @field:Json(name = "hourly")
    val WeatherData: WeatherDataDto)
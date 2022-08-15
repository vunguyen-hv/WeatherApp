package com.plcoding.weatherapp.domain.weather

data class WeatherInfo(
    val weatherDayPerDay: Map<Int, List<WeatherData>>,
    val currentWeather: WeatherData?)
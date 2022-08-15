package com.plcoding.weatherapp.data.mappers

import com.plcoding.weatherapp.data.network.WeatherDataDto
import com.plcoding.weatherapp.data.network.WeatherDto
import com.plcoding.weatherapp.domain.weather.WeatherData
import com.plcoding.weatherapp.domain.weather.WeatherInfo
import com.plcoding.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(val index: Int, val data: WeatherData)

fun WeatherDataDto.toWeatherDataMap():Map<Int, List<WeatherData>>{
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val pressure = pressures[index]
        val windSpeed = windSpeeds[index]
        val humility = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humility,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index /24
    }.mapValues {
        it.value.map{
            it.data
        }
    }

}

fun WeatherDto.toWeatherInfo(): WeatherInfo{
    val weatherDayByDay = WeatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeather = weatherDayByDay[0]?.find{
        val hour = if(now.minute >= 30) {
            if(now.hour == 23) 1
            else now.hour + 1
        }
        else now.hour
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDayByDay,
        currentWeather
    )


}

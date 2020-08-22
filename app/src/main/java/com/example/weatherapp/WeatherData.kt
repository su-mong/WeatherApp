package com.example.weatherapp

data class WeatherData(
    val weather: weather,
    val main: main,
    val wind: wind,
    val clouds: clouds
)

data class weather(
    val weather: ArrayList<weatherSub>
)

data class weatherSub(
    val main: String
)

data class main(
    val temp: Double,
    val humidity: Double
)

data class wind(
    val speed: Double
)

data class clouds(
    val all: Double
)
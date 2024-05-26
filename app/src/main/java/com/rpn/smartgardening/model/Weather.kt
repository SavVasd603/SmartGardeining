package com.rpn.smartgardening.model


data class Weather(
    var humidity: Int = 0,
    var soilMoisture: Int = 0,
    var temprature: Int = 0,
    var waterLevel: Int = 0,
    var gas: Int = 0,
    var light: Int = 0,
    var ph: Float = 0.0f
)

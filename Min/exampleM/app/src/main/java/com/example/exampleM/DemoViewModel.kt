package com.example.exampleM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import java.lang.Exception
import kotlin.math.roundToInt

class DemoViewModel {
    var isFahrenheit by mutableStateOf(true)
    var result by mutableStateOf("")

    fun convertTemp(temp: String) {
        result = try {
            val tempInt = temp.toInt()
            val converted = if (isFahrenheit) {
                (tempInt - 32) * 0.5556
            } else {
                (tempInt * 1.8) + 32
            }
            converted.roundToInt().toString()
        } catch (e: Exception) {
            "Invalid Entry"
        }
    }

    fun switchChange() {
        isFahrenheit = !isFahrenheit
    }
}
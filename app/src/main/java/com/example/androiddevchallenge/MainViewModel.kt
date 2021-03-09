package com.example.androiddevchallenge

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit


data class Time(val millis: Long) {
    private val hours = TimeUnit.MILLISECONDS.toHours(millis)
    private val leftMinutes =
        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours)
    private val leftSeconds =
        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(leftMinutes)

    override fun toString(): String {
        return String.format(
            "%02d:%02d:%02d",
            hours,
            leftMinutes,
            leftSeconds
        )
    }
}

class MainViewModel : ViewModel() {
    var timeTextValue by mutableStateOf(TextFieldValue(""))
    var time by mutableStateOf(Time(0))
        private set
    var counterVisible by mutableStateOf(false)

    fun setTime(timeInSeconds: Int) {
        time = Time(timeInSeconds * 1000L)
    }

    fun startTimer() {
        counterVisible = true
        object : CountDownTimer(time.millis, 1000) {
            override fun onTick(timeInMillis: Long) {
                time = Time(timeInMillis)
            }

            override fun onFinish() {
                Log.d("TIMER", "Finish!")
            }
        }.start()
    }

    companion object {
        const val MILLIS_TO_SECONDS_FACTOR = 1000L
    }
}
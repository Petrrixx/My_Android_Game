package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val _castleHealth = MutableStateFlow(100)
    val castleHealth: StateFlow<Int> get() = _castleHealth

    private val _level = MutableStateFlow(1)
    val level: StateFlow<Int> get() = _level

    private val _time = MutableStateFlow("00:00")
    val time: StateFlow<String> get() = _time

    private val _coins = MutableStateFlow(50)
    val coins: StateFlow<Int> get() = _coins

    private val _specialCoins = MutableStateFlow(10)
    val specialCoins: StateFlow<Int> get() = _specialCoins

    private val _joystickX = MutableStateFlow(0f)
    val joystickX: StateFlow<Float> get() = _joystickX

    private val _joystickY = MutableStateFlow(0f)
    val joystickY: StateFlow<Float> get() = _joystickY

    private val _directions = MutableStateFlow(
        Directions(
            up = false,
            down = false,
            left = false,
            right = false,
            upLeft = false,
            upRight = false,
            downLeft = false,
            downRight = false
        )
    )
    val directions: StateFlow<Directions> get() = _directions

    private var timerJob: Job? = null

    fun updateDirections(
        up: Boolean, down: Boolean, left: Boolean, right: Boolean,
        upLeft: Boolean, upRight: Boolean, downLeft: Boolean, downRight: Boolean
    ) {
        _directions.value = Directions(up, down, left, right, upLeft, upRight, downLeft, downRight)
    }

    data class Directions(
        val up: Boolean,
        val down: Boolean,
        val left: Boolean,
        val right: Boolean,
        val upLeft: Boolean,
        val upRight: Boolean,
        val downLeft: Boolean,
        val downRight: Boolean
    )

    fun startTimer() {
        if (timerJob == null) {
            timerJob = viewModelScope.launch {
                var seconds = 0
                while (true) {
                    delay(1000)
                    seconds++
                    val minutes = seconds / 60
                    val remainingSeconds = seconds % 60
                    _time.value = String.format("%02d:%02d", minutes, remainingSeconds)
                }
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }
}

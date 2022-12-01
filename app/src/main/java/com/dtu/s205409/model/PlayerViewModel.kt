package com.dtu.s205409.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.*

class PlayerViewModel : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _points = mutableStateOf(0)
    val points: State<Int> = _points

    private val _lives = mutableStateOf(5)
    val lives: State<Int> = _lives

    private val _guesses = mutableStateOf(5)
    val guesses: State<Int> = _guesses

    fun addPoints(points: Int) {
        _points.value += points
    }

    fun removeLifePoint() {
        _lives.value--
    }

    fun resetPoints() {
        _points.value = 0
    }

    fun removePoints(points: Int) {
        _points.value -= points
    }

    fun removeAllLives() {
        _lives.value = 0
    }

    fun reset() {
        _lives.value = 5
        _points.value = 0
        _guesses.value = 0
    }

    fun setName(name: String) {
        _name.value = capitalize(name)
    }

    private fun capitalize(input: String) : String {
        return input.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }

}
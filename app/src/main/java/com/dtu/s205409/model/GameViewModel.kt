package com.dtu.s205409.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dtu.s205409.utility.GameWord
import java.util.*

class GameViewModel : ViewModel() {

    private val _pointList = mutableStateOf(listOf("Fallit", "100", "300", "500", "600", "800", "1000", "1500"))

    private val _pointListResult = mutableStateOf("")
    val pointListResult: State<String> = _pointListResult

    // TODO REWORK
    private val _guessedInputs = mutableStateOf(ArrayList<String>())
    val guessedInputs: State<ArrayList<String>> = _guessedInputs

    private val _input = mutableStateOf("")
    val input: State<String> = _input

    private val _randomWord = mutableStateOf(GameWord.PLACEHOLDER)
    val randomWord: State<GameWord> = _randomWord

    private val _hasSpinned = mutableStateOf(false)
    val hasSpinned: State<Boolean> = _hasSpinned

    fun reset(playerViewModel: PlayerViewModel) {
        playerViewModel.reset()
        _randomWord.value = GameWord.PLACEHOLDER
        _hasSpinned.value = false
        _pointListResult.value = ""
        _input.value = ""
        _guessedInputs.value.clear()
    }

    fun generateRandomWord() : GameWord {
        val random = Random()
        val randomIndex = random.nextInt(GameWord.values().size)
        while (GameWord.values()[randomIndex] == GameWord.PLACEHOLDER) {
            return generateRandomWord()
        }
        return GameWord.values()[randomIndex]
    }

    fun generateRandomPointResult() {
        val random = Random()
        val randomIndex = random.nextInt(_pointList.value.size)
        _pointListResult.value = _pointList.value[randomIndex]
    }

    fun displayRandomWord() : String {
        val stringBuilder = StringBuilder()
        for (i in 0 until _randomWord.value.word.length) {
            if (_randomWord.value.word[i].toString().contains("-")) {
                stringBuilder.append("-")
                continue
            }
            if (_randomWord.value.word[i].toString().contains(" ")) {
                stringBuilder.append(" ")
                continue
            }
            if (_guessedInputs.value.contains(_randomWord.value.word[i].toString().lowercase()))
                stringBuilder.append(_randomWord.value.word[i])
            else
                stringBuilder.append("*")
        }
        return stringBuilder.toString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    fun checkInput() : Boolean {
        return _input.value.isEmpty() || _input.value.length > 1 || _input.value in _guessedInputs.value
    }

    fun getInputErrorMessage() : String {
        if (_input.value in _guessedInputs.value)
            return "Du har allerede gættet på ${_input.value}"
        if (_input.value.length > 1 || _input.value.isEmpty())
            return "Dit gæt kan kun være et bogstav"
        if (_input.value.toCharArray()[0].isDigit())
            return "Dit gæt kan ikke være et tal"
        return ""
    }

    fun isCorrectGuess() : Boolean {
        for (i in 0 until _randomWord.value.word.length) {
            if (_input.value.equals(_randomWord.value.word[i].toString(), ignoreCase = true))
                return true
        }
        return false
    }

    fun isFallit() : Boolean {
        return _pointListResult.value.equals("fallit", ignoreCase = true)
    }

    fun hasWon(word: String) : Boolean {
        for (element in word) {
            if (element == '*')
                return false
        }
        return true
    }

    fun resultMessage() : String {
        if (isFallit())
            return "Øv! Du ramte fallit og mister alle dine points"
        return "+${_pointListResult.value} points hvis du gætter rigtigt!"
    }

    fun setRandomWord(gameWord: GameWord) {
        _randomWord.value = gameWord
        _randomWord.value.word = gameWord.word.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
    }

    fun setInput(input: String) {
        _input.value = input
    }

    fun resetSpin() {
        _hasSpinned.value = false
        _pointListResult.value = ""
        _input.value = ""
    }

    fun toggleSpinned() {
        _hasSpinned.value = !_hasSpinned.value
    }

}
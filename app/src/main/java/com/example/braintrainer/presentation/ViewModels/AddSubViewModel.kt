package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.presentation.uiStates.MathUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddSubViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _uiState = MutableStateFlow(MathUiState())
    val uiState = _uiState.asStateFlow()
    private val gameId = checkNotNull(savedStateHandle["gameId"])
    private var timerJob: Job? = null
    private var isTimerRunning = false
    private var remainingTime = _uiState.value.timer

    init {
        _uiState.value = _uiState.value.copy(gameId = gameId.toString())
        startTimer()
        generateNewOperation()
    }

    private fun startTimer() {
        if (!isTimerRunning) { // Comprueba si el temporizador ya se está ejecutando
            isTimerRunning = true
            timerJob = viewModelScope.launch {
                for (i in remainingTime downTo 0) {
                    _uiState.value = _uiState.value.copy(timer = i)
                    delay(1000)
                    remainingTime--
                }
            }
        }
    }

    private fun generateNewOperation() {
        val num1 = Random.nextInt(21)
        val num2 = Random.nextInt(21)
        // Randomly choose between addition and subtraction
        val operation = if (Random.nextBoolean()) "+" else "-"
        val correctAnswer = if (operation == "+") num1 + num2 else num1 - num2
        val answers = mutableListOf(correctAnswer)
        while (answers.size < 4) {
            val incorrectAnswer = Random.nextInt(41) - 20
            if (incorrectAnswer != correctAnswer && !answers.contains(incorrectAnswer)) {
                answers.add(incorrectAnswer)
            }
        }
        answers.shuffle()

        _uiState.value =
            _uiState.value.copy(num1 = num1, num2 = num2, operation = operation, answers = answers)
    }

    fun checkAnswer(selectedAnswer: Int) {
        val correctAnswer = if (_uiState.value.operation == "+") {
            _uiState.value.num1 + _uiState.value.num2
        } else {
            _uiState.value.num1 - _uiState.value.num2
        }
        val isCorrect = selectedAnswer == correctAnswer

        if (isCorrect) {
            _uiState.value = _uiState.value.copy(points = _uiState.value.points + 1)
        }

        _uiState.value = _uiState.value.copy(showResult = true, isCorrect = isCorrect, hasAnswered = true)

        viewModelScope.launch {
            timerJob?.cancel()
            isTimerRunning = false
            remainingTime = _uiState.value.timer
            delay(1000)
            //_uiState.value = _uiState.value.copy(showResult = false, hasAnswered = false)

            if (_uiState.value.currentOperation < _uiState.value.maxOperations && remainingTime > 0) {
                _uiState.value =
                    _uiState.value.copy(
                        currentOperation = _uiState.value.currentOperation + 1,
                        showResult = false,
                        hasAnswered = false // Podría usar hasAnswered para bloquear los botones
                    )
                generateNewOperation()
                startTimer()
            }
        }
    }
}
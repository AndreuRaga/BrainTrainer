package com.example.braintrainer.presentation.ViewModels

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
class MathViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MathUiState())
    val uiState = _uiState.asStateFlow()
    private var timerJob: Job? = null
    private var isTimerRunning = false
    private var remainingTime = _uiState.value.timer

    init {
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
                // Lógica para cuando el temporizador llega a 0
            }
        }
    }

    private fun generateNewOperation() {
        val num1 = Random.nextInt(21)
        val num2 = Random.nextInt(21)
        _uiState.value = _uiState.value.copy(num1 = num1, num2 = num2)
        val correctAnswerPosition = Random.nextInt(4)
        for (i in 0..3) {
            if (i == correctAnswerPosition) {
                _uiState.value.answers[i] = num1 + num2
            } else {
                var incorrectAnswer = Random.nextInt(41)
                while (incorrectAnswer == num1 + num2) {
                    incorrectAnswer = Random.nextInt(41)
                }
                _uiState.value.answers[i] = incorrectAnswer
            }
        }
    }

    fun checkAnswer(selectedAnswer: Int): Boolean {
        val correctAnswer = _uiState.value.num1 + _uiState.value.num2
        if (selectedAnswer == correctAnswer) {
            _uiState.value = _uiState.value.copy(points = _uiState.value.points + 1)
        }
        _uiState.value = _uiState.value.copy(showResult = true)
        viewModelScope.launch {
            timerJob?.cancel()
            isTimerRunning = false // Indica que el temporizador no se está ejecutando
            remainingTime = _uiState.value.timer
            delay(500)
            _uiState.value = _uiState.value.copy(showResult = false)
            if (_uiState.value.operationCount < _uiState.value.maxOperations) {
                _uiState.value = _uiState.value.copy(operationCount = _uiState.value.operationCount + 1)
                generateNewOperation()
                startTimer() // Reinicia el temporizador si no ha llegado a 0
            }
        }
        return selectedAnswer == correctAnswer
    }
}
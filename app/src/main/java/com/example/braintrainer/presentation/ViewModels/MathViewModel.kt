package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.presentation.uiStates.MathUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        generateNewOperation()
    }

    private fun generateNewOperation() {
        val num1 = Random.nextInt(21)
        val num2 = Random.nextInt(21)
        val currentPoints = _uiState.value.points
        _uiState.value = MathUiState(num1, num2, points = currentPoints)
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
            _uiState.value = _uiState.value.copy(points = _uiState.value.points + 1, showResult = true)
        }
        viewModelScope.launch {
            delay(1000)
            _uiState.value = _uiState.value.copy(showResult = false)
            generateNewOperation()
        }
        return selectedAnswer == correctAnswer
    }
}
package com.example.braintrainer.presentation.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.braintrainer.presentation.uiStates.MathUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        _uiState.value = MathUiState(num1, num2)
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

    fun checkAnswer(selectedAnswer: Int) {
        val correctAnswer = _uiState.value.num1 + _uiState.value.num2
        Log.d("MathViewModel", "Correct answer: $correctAnswer")
    }
}
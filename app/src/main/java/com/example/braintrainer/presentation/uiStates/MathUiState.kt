package com.example.braintrainer.presentation.uiStates

data class MathUiState(
    val num1: Int = 0,
    val num2: Int = 0,
    val answers: List<Int> = listOf(0, 0, 0, 0),
    val points: Int = 0,
    val timer: Int = 35,
    val currentOperation: Int = 1,
    val maxOperations: Int = 25,
    val showResult: Boolean = false,
    val isCorrect: Boolean = false
)

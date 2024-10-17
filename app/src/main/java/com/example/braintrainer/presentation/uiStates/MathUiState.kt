package com.example.braintrainer.presentation.uiStates

data class MathUiState(
    val gameId: String = "",
    val num1: Int = 0,
    val num2: Int = 0,
    val operation: String = "+",
    val answers: List<Int> = listOf(0, 0, 0, 0),
    val points: Int = 0,
    val timer: Int = 35,
    val currentOperation: Int = 1,
    val maxOperations: Int = 5,
    val showResult: Boolean = false,
    val isCorrect: Boolean = false,
    val hasAnswered: Boolean = false
)

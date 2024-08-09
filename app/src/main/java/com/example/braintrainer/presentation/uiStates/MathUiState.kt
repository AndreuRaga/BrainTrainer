package com.example.braintrainer.presentation.uiStates

data class MathUiState(
    val num1: Int = 0,
    val num2: Int = 0,
    val answers: MutableList<Int> = mutableListOf(0, 0, 0, 0),
    val points: Int = 0,
    val showResult: Boolean = false,
    val timer: Int = 0
)

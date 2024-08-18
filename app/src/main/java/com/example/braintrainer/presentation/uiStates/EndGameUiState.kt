package com.example.braintrainer.presentation.uiStates

data class EndGameUiState(
    val gameId: String = "",
    val currentPoints: Int = 0,
    val bestScore: Int = 0,
    val isNewRecord: Boolean = false
)

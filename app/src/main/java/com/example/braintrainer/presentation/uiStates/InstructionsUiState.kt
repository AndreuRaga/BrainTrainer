package com.example.braintrainer.presentation.uiStates

data class InstructionsUiState(
    val gameId: String = "",
    val gameName: String = "",
    val gameInstructions: String = "",
    val bestScore: Int? = null
)
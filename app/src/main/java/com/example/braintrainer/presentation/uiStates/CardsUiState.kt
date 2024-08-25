package com.example.braintrainer.presentation.uiStates

data class CardsUiState(
    val gameId: String = "",
    val cards: List<CardData> = emptyList(),
    val isGameBlocked: Boolean = false, //Establece si se puede interactuar con las cartas o no
    val points: Int = 0,
    val attempts: Int = 0,
    val maxAttempts: Int = 12,
    val timeLeft: Int = 5 // Tiempo inicial en segundos
)

data class CardData(
    val id: Int,
    val image: Int,
    var isRevealed: Boolean = false
)
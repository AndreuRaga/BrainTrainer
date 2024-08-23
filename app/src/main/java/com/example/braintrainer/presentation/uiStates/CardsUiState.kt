package com.example.braintrainer.presentation.uiStates

data class CardsUiState(
    val cards: List<CardData> = emptyList(),
    val canRevealCards: Boolean = true // Indica si se pueden revelar más cartas
)

data class CardData(
    val id: Int,
    val image: Int,
    var isRevealed: Boolean = false
)
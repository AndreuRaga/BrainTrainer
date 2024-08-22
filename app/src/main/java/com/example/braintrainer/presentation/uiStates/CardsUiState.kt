package com.example.braintrainer.presentation.uiStates

data class CardsUiState(
    val cards: List<CardData> = emptyList()
)

data class CardData(
    val id: Int,
    val image: Int,
    var isRevealed: Boolean = false
)

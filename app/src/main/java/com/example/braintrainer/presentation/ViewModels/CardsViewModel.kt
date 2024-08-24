package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.R
import com.example.braintrainer.presentation.uiStates.CardData
import com.example.braintrainer.presentation.uiStates.CardsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _uiState = MutableStateFlow(CardsUiState())
    val uiState = _uiState.asStateFlow()

    private val gameId = checkNotNull(savedStateHandle["gameId"])

    private var cardsToCompare: List<CardData> = emptyList()

    init {
        _uiState.value = _uiState.value.copy(gameId = gameId.toString())
        viewModelScope.launch {
            startGame()
        }
    }

    private suspend fun startGame() {
        val images = listOf(
            R.drawable.card0,
            R.drawable.card1,
            R.drawable.card2,
            R.drawable.card3,
            R.drawable.card4,
            R.drawable.card5,
            R.drawable.card6,
            R.drawable.card7
        )
        val pairs = (images + images).shuffled()
        _uiState.update {
            it.copy(
                cards = pairs.mapIndexed { index, image -> CardData(index, image, true) },
                areCardsBlocked = true
            )
        }
        delay(2000L)
        _uiState.update {
            it.copy(
                cards = it.cards.map { it.copy(isRevealed = false) },
                areCardsBlocked = false
            )
        }
    }

    fun onCardClicked(card: CardData) {
        if (uiState.value.areCardsBlocked || card.isRevealed) return
        viewModelScope.launch {
            revealCard(card)
            checkMatch()
        }
    }

    private fun revealCard(card: CardData) {
        _uiState.update { currentState ->
            val updatedCards = currentState.cards.map {
                if (it.id == card.id) it.copy(isRevealed = true) else it
            }
            currentState.copy(cards = updatedCards)
        }
        cardsToCompare += card
    }

    private suspend fun checkMatch() {
        if (cardsToCompare.size == 2) {
            _uiState.update { it.copy(areCardsBlocked = true) }
            if (cardsToCompare[0].image == cardsToCompare[1].image) {
                onCardsMatch()
            } else {
                onCardsMismatch()
            }
        }
    }

    private fun onCardsMatch() {
        _uiState.update { currentState ->
            currentState.copy(
                points = currentState.points + 1,
                attempts = currentState.attempts + 1
            )
        }
        resetSelection()
    }

    private suspend fun onCardsMismatch() {
        delay(1000L)
        _uiState.update { currentState ->
            val updatedCards = currentState.cards.map {
                if (it.id == cardsToCompare[0].id || it.id == cardsToCompare[1].id) it.copy(
                    isRevealed = false
                ) else it
            }
            currentState.copy(
                cards = updatedCards,
                attempts = currentState.attempts + 1
            )
        }
        resetSelection()
    }

    private fun resetSelection() {
        cardsToCompare = emptyList()
        _uiState.update { it.copy(areCardsBlocked = false) }
    }
}
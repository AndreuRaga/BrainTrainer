package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.braintrainer.R
import com.example.braintrainer.presentation.uiStates.CardData
import com.example.braintrainer.presentation.uiStates.CardsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _uiState = MutableStateFlow(CardsUiState())
    val uiState = _uiState.asStateFlow()
    private val gameId = checkNotNull(savedStateHandle["gameId"])

    private var revealedCards = 0

    suspend fun startGame() {
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
            it.copy(cards = pairs.mapIndexed { index, image -> CardData(index, image, true) })
        }
        delay(2000L)
        _uiState.update {
            it.copy(cards = it.cards.map { it.copy(isRevealed = false) })
        }
    }

    fun onCardClicked(cardId: Int) {
        if (revealedCards >= 2) return

        _uiState.update { currentState ->
            val updatedCards = currentState.cards.toMutableList().also { cards ->
                val cardIndex = cards.indexOfFirst { it.id == cardId }
                if (cardIndex != -1) {
                    cards[cardIndex] = cards[cardIndex].copy(isRevealed = !cards[cardIndex].isRevealed)
                }
            }
            revealedCards = updatedCards.count { it.isRevealed }
            currentState.copy(cards = updatedCards, canRevealCards = revealedCards < 2)
        }
    }
}
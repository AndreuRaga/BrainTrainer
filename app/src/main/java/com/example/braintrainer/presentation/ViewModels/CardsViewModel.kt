package com.example.braintrainer.presentation.ViewModels

import android.util.Log
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

    private var firstCard: CardData? = null
    private var secondCard: CardData? = null
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
        if (!_uiState.value.areCardsBlocked) {
            viewModelScope.launch {
                checkMatch(card)
            }
        }
    }

    private suspend fun checkMatch(card: CardData) {
        if (firstCard == null) {
            _uiState.update {
                it.copy(cards = it.cards.map { if (it == card) it.copy(isRevealed = true) else it })
            }
            firstCard = card
        } else if (secondCard == null) {
            _uiState.update {
                it.copy(cards = it.cards.map { if (it == card) it.copy(isRevealed = true) else it })
            }
            secondCard = card
            _uiState.update { it.copy(areCardsBlocked = true) }
            if (firstCard!!.image == secondCard!!.image) { // Si las cartas coinciden
                firstCard = null
                secondCard = null
                _uiState.update {
                    it.copy(
                        areCardsBlocked = false,
                        points = it.points + 1,
                        attempts = it.attempts + 1
                    )
                }
            } else { // Si las cartas no coinciden
                delay(1000L)
                _uiState.update {
                    it.copy(cards = it.cards.map {
                        if (it.id == firstCard!!.id || it.id == secondCard!!.id) it.copy(
                            isRevealed = false
                        ) else it
                    })
                }
                firstCard = null
                secondCard = null
                _uiState.update {
                    it.copy(
                        areCardsBlocked = false,
                        attempts = it.attempts + 1
                    )
                }
            }
            val points = _uiState.value.points
            val numberOfPairs = _uiState.value.cards.size / 2
            val attempts = _uiState.value.attempts
            val maxAttempts = _uiState.value.maxAttempts
            if (points == numberOfPairs || attempts == maxAttempts) { // Fin del juego
                // Lógica para navegar a EndGameScreen
                Log.d("MathScreen", "Fin del juego")
            }
        }
    }
}
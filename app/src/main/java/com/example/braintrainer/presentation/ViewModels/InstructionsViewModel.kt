package com.example.braintrainer.presentation.ViewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.data.repositories.AuthRepository
import com.example.braintrainer.data.repositories.GameCategoryRepository
import com.example.braintrainer.data.repositories.ScoreRepository
import com.example.braintrainer.presentation.uiStates.InstructionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructionsViewModel @Inject constructor(
    private val gameCategoryRepository: GameCategoryRepository,
    private val scoreRepository: ScoreRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel()  {
    private val gameId: String = checkNotNull(savedStateHandle["gameId"])
    private val _uiState = MutableStateFlow(InstructionsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            gameCategoryRepository.getGameByIdFromDB(gameId)
                .onSuccess { game ->
                    _uiState.value = InstructionsUiState(
                        gameId = gameId,
                        gameName = game?.name ?: "",
                        gameInstructions = game?.instructions ?: ""
                    )
                    fetchBestScore(gameId)
                }
                .onFailure { exception ->
                    Log.e("InstructionsViewModel", "Error al obtener el juego", exception)
                }
        }
    }

    private suspend fun fetchBestScore(gameId: String) {
        val userId = authRepository.getCurrentUser()?.uid
        if (userId != null) {
            scoreRepository.getScore(userId, gameId)
                .onSuccess { bestScore ->
                    _uiState.value = _uiState.value.copy(bestScore = bestScore)
                }
                .onFailure { exception ->
                    Log.e("InstructionsViewModel", "Error al obtener la mejor puntuación", exception)}
        }
    }
}
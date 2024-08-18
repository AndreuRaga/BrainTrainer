package com.example.braintrainer.presentation.ViewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.data.repositories.AuthRepository
import com.example.braintrainer.data.repositories.ScoreRepository
import com.example.braintrainer.presentation.uiStates.EndGameUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EndGameViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(EndGameUiState())
    val uiState = _uiState.asStateFlow()

    private val gameId: String = checkNotNull(savedStateHandle["gameId"])
    private val points: Int = checkNotNull(savedStateHandle["points"])

    init {
        _uiState.value = _uiState.value.copy(gameId = gameId, currentPoints = points)
        viewModelScope.launch {
            val userId = getCurrentUserId()
            if (userId != null) {
                fetchAndSaveScore(userId)
            } else {
                Log.e("EndGameViewModel", "No se pudo obtener el ID del usuario actual")
            }
        }
    }

    private suspend fun fetchAndSaveScore(userId: String) {
        scoreRepository.getScore(userId, gameId)
            .onSuccess { bestScore ->
                _uiState.value = _uiState.value.copy(bestScore = bestScore ?: 0)
                _uiState.value = _uiState.value.copy(isNewRecord = points > (bestScore ?: 0))
                saveScore(userId)
            }
            .onFailure { exception ->
                Log.e("EndGameViewModel", "Error al obtener la puntuación", exception)
            }
    }

    private suspend fun saveScore(userId: String) {
        scoreRepository.saveScore(userId, gameId, points)
            .onSuccess {
                Log.d("EndGameViewModel", "Puntuación guardada correctamente")
            }
            .onFailure { exception ->
                Log.e("EndGameViewModel", "Error al guardar la puntuación", exception)
            }
    }

    private fun getCurrentUserId(): String? {
        return authRepository.getCurrentUser()?.uid
    }
}
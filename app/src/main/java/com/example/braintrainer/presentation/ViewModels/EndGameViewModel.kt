package com.example.braintrainer.presentation.ViewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.data.repositories.AuthRepository
import com.example.braintrainer.data.repositories.ScoreRepository
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
        _uiState.value = _uiState.value.copy(gameId = gameId, points = points)
        saveScore()
    }
    private fun saveScore() {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUser()?.uid
            if (userId != null) {
                scoreRepository.saveScore(userId, gameId, points)
                    .onSuccess {
                        // Manejar el éxito (opcional)
                        Log.d("EndGameViewModel", "Puntuación guardada correctamente")
                    }
                    .onFailure { exception ->
                        // Manejar el error
                        Log.e("EndGameViewModel", "Error al guardar la puntuación", exception)
                    }
            } else {
                Log.e("EndGameViewModel", "No se pudo obtener el ID del usuario actual")
            }
        }
    }
}
//EndGameUiState.kt
data class EndGameUiState(
    val gameId: String = "",
    val points: Int = 0
)
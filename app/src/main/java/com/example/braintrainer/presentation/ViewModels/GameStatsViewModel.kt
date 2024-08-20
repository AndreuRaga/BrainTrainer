package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.data.models.GameCategory
import com.example.braintrainer.data.repositories.AuthRepository
import com.example.braintrainer.data.repositories.GameCategoryRepository
import com.example.braintrainer.data.repositories.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameStatsViewModel @Inject constructor(
    private val gameCategoryRepository: GameCategoryRepository,
    private val scoreRepository: ScoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<GameStatsUiState>(GameStatsUiState.Loading)
    val uiState: StateFlow<GameStatsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadGameStats()
        }
    }

    private suspend fun loadGameStats() {
        _uiState.update { GameStatsUiState.Loading }
        gameCategoryRepository.getCategoriesFromDB()
            .onSuccess { categories ->
                val userId = authRepository.getCurrentUser()?.uid
                if (userId != null) {
                    val categoriesWithStats = categories.map { category ->
                        fetchCategoryStats(category, userId)
                    }
                    _uiState.update { GameStatsUiState.Success(categoriesWithStats) }
                } else {
                    _uiState.update { GameStatsUiState.Error("No se pudo obtener el ID del usuario") }
                }
            }
            .onFailure { e ->
                _uiState.update { GameStatsUiState.Error(e.message ?: "Error al cargar las estadísticas") }
            }
    }

    private suspend fun fetchCategoryStats(category: GameCategory, userId: String): GameCategory {
        val gamesWithStats = category.games.map { game ->
            val bestScore = scoreRepository.getScore(userId, game.id).getOrNull() ?: 0
            game.copy(bestScore = bestScore)
        }
        return category.copy(games = gamesWithStats)
    }
}

// GameStatsUiState.kt
sealed class GameStatsUiState {
    object Loading : GameStatsUiState()
    data class Success(val categories: List<GameCategory>) : GameStatsUiState()
    data class Error(val message: String) : GameStatsUiState()
}
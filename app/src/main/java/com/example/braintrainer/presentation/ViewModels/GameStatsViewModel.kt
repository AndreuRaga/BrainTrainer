package com.example.braintrainer.presentation.ViewModels

import android.util.Log
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameStatsViewModel @Inject constructor(
    private val gameCategoryRepository: GameCategoryRepository,
    private val scoreRepository: ScoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GameStatsUiState())
    val uiState: StateFlow<GameStatsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadGameStats()
        }
    }

    private suspend fun loadGameStats() {
        gameCategoryRepository.getCategoriesFromDB()
            .onSuccess { categories ->
                val userId = authRepository.getCurrentUser()?.uid
                if (userId != null) {
                    val categoriesWithStats = categories.map { category ->
                        val gamesWithStats = category.games.map { game ->
                            val bestScore = scoreRepository.getScore(userId, game.id).getOrNull() ?: 0
                            Log.d("GameStatsViewModel", "Best score for ${game.name}: $bestScore")
                            game.copy(bestScore = bestScore)
                        }
                        category.copy(games = gamesWithStats)
                    }
                    _uiState.value = _uiState.value.copy(categories = categoriesWithStats)
                } else {
                    Log.e("GameStatsViewModel", "No se pudo obtener el ID del usuario actual")
                }
            }.onFailure { e ->
                Log.e("GameStatsViewModel", "Error al cargar las estadísticas", e)
            }
    }
}

data class GameStatsUiState(
    val categories: List<GameCategory> = emptyList(),
)
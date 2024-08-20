package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.data.models.GameCategory
import com.example.braintrainer.data.repositories.AuthRepository
import com.example.braintrainer.data.repositories.GameCategoryRepository
import com.example.braintrainer.data.repositories.ScoreRepository
import com.example.braintrainer.presentation.uiStates.StatsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneralStatsViewModel @Inject constructor(
    private val gameCategoryRepository: GameCategoryRepository,
    private val scoreRepository: ScoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<StatsUiState>(StatsUiState.Loading)
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadGeneralStats()
        }
    }

    private suspend fun loadGeneralStats() {
        _uiState.update { StatsUiState.Loading }
        val result = gameCategoryRepository.getCategoriesFromDB()
        result
            .onSuccess{ categories ->
                val userId = authRepository.getCurrentUser()?.uid
                if (userId != null) {
                    val categoriesWithStats = categories.map { category ->
                        calculateCategoryStats(category, userId)
                    }
                    _uiState.update { StatsUiState.Success(categoriesWithStats) }
                } else {
                    _uiState.update { StatsUiState.Error("No se pudo obtener el ID del usuario") }
                }
            }
            .onFailure { e ->
                _uiState.update { StatsUiState.Error(e.message ?: "Error al cargar las estadísticas") }
            }
    }

    private suspend fun calculateCategoryStats(category: GameCategory, userId: String): GameCategory {
        val (totalBestScore, totalMaxScore) = category.games.fold(0 to 0) { acc, game ->
            val bestScore = scoreRepository.getScore(userId, game.id).getOrNull() ?: 0
            (acc.first + bestScore) to (acc.second + game.maxScore)
        }
        val categoryProgress = if (totalMaxScore > 0) totalBestScore.toFloat() / totalMaxScore else 0f
        return category.copy(
            totalBestScore = totalBestScore,
            totalMaxScore = totalMaxScore,
            progress = categoryProgress
        )
    }
}
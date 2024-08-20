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
class GeneralStatsViewModel @Inject constructor(
    private val gameCategoryRepository: GameCategoryRepository,
    private val scoreRepository: ScoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<GeneralStatsUiState>(GeneralStatsUiState.Loading)
    val uiState: StateFlow<GeneralStatsUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            loadGeneralStats()
        }
    }


    private suspend fun loadGeneralStats() {
        gameCategoryRepository.getCategoriesFromDB()
            .onSuccess { categories ->
                val userId = authRepository.getCurrentUser()?.uid
                if (userId != null) {
                    val categoriesWithStats = categories.map { category ->
                        val (totalBestScore, totalMaxScore) = category.games.fold(0 to 0) { acc, game ->
                            val bestScore = scoreRepository.getScore(userId, game.id).getOrNull() ?: 0
                            (acc.first + bestScore) to (acc.second + game.maxScore)
                        }
                        val categoryProgress = if (totalMaxScore > 0) totalBestScore.toFloat() / totalMaxScore else 0f
                        category.copy(totalBestScore = totalBestScore, totalMaxScore = totalMaxScore, progress = categoryProgress)
                    }
                    _uiState.update { GeneralStatsUiState.Success(categoriesWithStats) }
                } else {
                    _uiState.update { GeneralStatsUiState.Error("No se pudo obtener el ID del usuario") }
                }
            }
            .onFailure { e ->
                _uiState.update { GeneralStatsUiState.Error(e.message ?: "Error al cargar las estadísticas") }
            }
    }

    fun getGameCategories(): List<GameCategory> {
        return gameCategoryRepository.getGameCategories()
    }
}

sealed class GeneralStatsUiState {
    object Loading : GeneralStatsUiState()
    data class Success(val categories: List<GameCategory>) : GeneralStatsUiState()
    data class Error(val message: String) : GeneralStatsUiState()
}
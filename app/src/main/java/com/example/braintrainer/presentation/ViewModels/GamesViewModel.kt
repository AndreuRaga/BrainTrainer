package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.ViewModel
import com.example.braintrainer.data.models.GameCategory
import com.example.braintrainer.data.repositories.GameCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val gameCategoryRepository: GameCategoryRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(GamesUiState())
    val uiState = _uiState.asStateFlow()
    init {
        setGameCategories()
    }

    private fun setGameCategories() {
        _uiState.value = _uiState.value.copy(gameCategories = gameCategoryRepository.getGameCategories())
    }
}

data class GamesUiState(
    val gameCategories: List<GameCategory> = emptyList()
)
package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.data.models.GameCategory
import com.example.braintrainer.data.repositories.GameCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val gameCategoryRepository: GameCategoryRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(GamesUiState())
    val uiState = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            getCategories()
        }
    }
    private suspend fun getCategories() {
        _uiState.value = _uiState.value.copy(gameCategories = gameCategoryRepository.getCategoriesFromDB())
    }

    private fun setGameCategories() {
        _uiState.value = _uiState.value.copy(gameCategories = gameCategoryRepository.getGameCategories())
    }
}

data class GamesUiState(
    val gameCategories: List<GameCategory> = emptyList()
)
package com.example.braintrainer.presentation.uiStates

import com.example.braintrainer.data.models.GameCategory

sealed class StatsUiState {
    object Loading : StatsUiState()
    data class Success(val categories: List<GameCategory>) : StatsUiState()
    data class Error(val message: String) : StatsUiState()
}
package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EndGameViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _uiState = MutableStateFlow(EndGameUiState())
    val uiState = _uiState.asStateFlow()
    private val gameId: String = checkNotNull(savedStateHandle["gameId"])
    private val points: Int = checkNotNull(savedStateHandle["points"])
    init {
        _uiState.value = _uiState.value.copy(gameId = gameId, points = points)
    }
}
//EndGameUiState.kt
data class EndGameUiState(
    val gameId: String = "",
    val points: Int = 0
)
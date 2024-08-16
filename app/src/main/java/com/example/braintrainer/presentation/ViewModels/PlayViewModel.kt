package com.example.braintrainer.presentation.ViewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.data.models.Game
import com.example.braintrainer.data.repositories.GameCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val gameCategoryRepository: GameCategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val gameId: String = checkNotNull(savedStateHandle["gameId"])
    private val _uiState = MutableStateFlow<Game?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            gameCategoryRepository.getGameByIdFromDB(gameId)
                .onSuccess { game ->
                    _uiState.value = game
                }
                .onFailure { exception ->
                    // Handle error
                    Log.e("PlayViewModel", "Error al obtener el juego", exception)
                }
        }

    }
}
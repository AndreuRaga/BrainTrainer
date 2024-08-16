package com.example.braintrainer.presentation.ViewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.data.repositories.GameCategoryRepository
import com.example.braintrainer.presentation.uiStates.InstructionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructionsViewModel @Inject constructor(
    private val gameCategoryRepository: GameCategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel()  {
    private val gameId: String = checkNotNull(savedStateHandle["gameId"])
    private val _uiState = MutableStateFlow(InstructionsUiState())
    val uiState = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            gameCategoryRepository.getGameByIdFromDB(gameId)
                .onSuccess { game ->
                    _uiState.value = InstructionsUiState(
                        gameId = gameId,
                        gameName = game?.name ?: "",
                        gameInstructions = game?.instructions ?: ""
                    )
                }
                .onFailure { exception ->
                    Log.e("InstructionsViewModel", "Error al obtener el juego", exception)
                }
        }
    }
}
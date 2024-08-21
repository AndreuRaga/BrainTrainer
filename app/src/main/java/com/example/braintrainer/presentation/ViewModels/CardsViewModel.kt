package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.braintrainer.presentation.uiStates.CardsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _uiState = MutableStateFlow(CardsUiState())
    val uiState = _uiState.asStateFlow()
    private val gameId = checkNotNull(savedStateHandle["gameId"])

}
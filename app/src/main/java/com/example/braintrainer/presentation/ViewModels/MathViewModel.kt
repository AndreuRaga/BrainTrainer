package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.ViewModel
import com.example.braintrainer.presentation.uiStates.MathUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MathViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MathUiState())
    val uiState = _uiState.asStateFlow()

    init {
        generateNewOperation()
    }

    private fun generateNewOperation() {
        val num1 = Random.nextInt(21)
        val num2 = Random.nextInt(21)
        _uiState.value = MathUiState(num1, num2)
    }
}
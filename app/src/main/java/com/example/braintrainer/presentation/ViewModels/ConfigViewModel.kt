package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.domain.AuthRepository
import com.example.braintrainer.presentation.uiStates.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun signOut() {
        authRepository.signOut()
        _uiState.value = _uiState.value.copy(isUserSignedIn = false)
    }

    fun deleteUser() {
        viewModelScope.launch {
            val success = authRepository.deleteUser()
            if (success) {
                _uiState.value = _uiState.value.copy(isUserSignedIn = false)
            } else {
                _uiState.value = _uiState.value.copy(
                    showErrorDialog = true,
                    errorMessage = "Error al borrar la cuenta"
                )
            }
        }
    }

    fun showErrorDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(showErrorDialog = show)
    }
}
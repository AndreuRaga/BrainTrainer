package com.example.braintrainer.presentation.uiStates

data class AuthUiState(
    val isUserSignedIn: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorMessage: String? = null
)
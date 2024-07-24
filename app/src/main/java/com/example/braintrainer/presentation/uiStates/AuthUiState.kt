package com.example.braintrainer.presentation.uiStates

data class AuthUiState(
    val isUserSignedIn: Boolean = false,
    val profilePictureUrl: String? = null,
    val userName: String? = null,
    val userEmail: String? = null,
    val showErrorDialog: Boolean = false,
    val errorMessage: String? = null
)
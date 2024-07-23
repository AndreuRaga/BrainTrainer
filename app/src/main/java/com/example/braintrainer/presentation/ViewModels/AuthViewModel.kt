package com.example.braintrainer.presentation.ViewModels

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainer.domain.AuthRepository
import com.example.braintrainer.presentation.uiStates.AuthUiState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val WEB_CLIENT_ID = "794704105067-crclh9gp6m36frubpulagpgjjc3tjuub.apps.googleusercontent.com"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val credentialManager: CredentialManager,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUserSignedIn = authRepository.isUserSignedIn())
        }
    }

    fun handleGoogleSignIn(context: Context) {
        viewModelScope.launch {
            googleOneTap(context) { success ->
                _uiState.value = _uiState.value.copy(
                    isUserSignedIn = success,
                    showErrorDialog = !success,
                    errorMessage = if (!success) "Error al iniciar sesión" else null
                )
            }
        }
    }

    private suspend fun googleOneTap(context: Context, onSignInComplete: (Boolean) -> Unit) {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

        // Check if the user has already signed in
        val googleIdOptionAlreadySignedIn = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(false)
            .setNonce(String(CharArray(16) { allowedChars.random() }))
            .build()

        // Check if the user has not signed in
        val googleIdOptionNotSignedIn = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(false)
            .setNonce(String(CharArray(16) { allowedChars.random() }))
            .build()

        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID)
            .setNonce(String(CharArray(16) { allowedChars.random() }))
            .build()

        val requestSignedIn = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptionAlreadySignedIn)
            .build()

        val requestNotSignedIn = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptionNotSignedIn)
            .build()

        val requestSignedInWithGoogle = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        withContext(Dispatchers.IO) {
            try {
                val result = credentialManager.getCredential(context, requestSignedIn)
                Log.d("SignedIn", "The user already signed in!!!")
                handleSignIn(result, onSignInComplete)
            } catch (e: GetCredentialException) {
                Log.d("SignedIn", "The user has not signed in yet!!!")
                try {
                    val result = credentialManager.getCredential(context, requestNotSignedIn)
                    handleSignIn(result, onSignInComplete)
                } catch (e: GetCredentialException) {
                    Log.d("SignedIn", "Something went very, very, very wrong!!!")
                    onSignInComplete(false) // Notifica fallo si ocurre un error
                }
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse, onSignInComplete: (Boolean) -> Unit) {
        val credential = result.credential
        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        Log.d("Credentials", "User successfully signed in!")
                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken, onSignInComplete)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("Credentials", "Invalid Google ID Token response", e)
                        onSignInComplete(false) // Notifica fallo si ocurre un error
                    }
                } else {
                    Log.e("Credentials", "Unexpected type of credential")
                    onSignInComplete(false) // Notifica fallo si ocurre un error
                }
            }
            else -> {
                Log.e("Credentials", "Unexpected type of credential")
                onSignInComplete(false) // Notifica fallo si ocurre un error
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String, onSignInComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = authRepository.signInWithCredential(idToken)
            onSignInComplete(success)
        }
    }

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
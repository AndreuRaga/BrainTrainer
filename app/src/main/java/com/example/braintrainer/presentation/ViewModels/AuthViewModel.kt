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
import com.example.braintrainer.data.repositories.AuthRepository
import com.example.braintrainer.data.repositories.UserRepository
import com.example.braintrainer.presentation.uiStates.AuthUiState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val WEB_CLIENT_ID = "794704105067-crclh9gp6m36frubpulagpgjjc3tjuub.apps.googleusercontent.com"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val credentialManager: CredentialManager,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()
    /*
    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isUserSignedIn = authRepository.getCurrentUser() != null,
                profilePictureUrl = authRepository.getCurrentUser()?.photoUrl.toString(),
                userName = authRepository.getCurrentUser()?.displayName,
                userEmail = authRepository.getCurrentUser()?.email
            )
        }
    }
     */

    fun handleGoogleSignIn(context: Context) {
        viewModelScope.launch {
            googleOneTapSignIn(context) { success ->
                val user = authRepository.getCurrentUser()
                _uiState.update {
                    it.copy(
                        isUserSignedIn = success,
                        profilePictureUrl = user?.photoUrl.toString(),
                        userName = user?.displayName,
                        userEmail = user?.email,
                        showErrorDialog = !success,
                        errorMessage = if (!success) "Error al iniciar sesión." else null
                    )
                }
                viewModelScope.launch {
                    if (success) {
                        createUser(user!!)
                    }
                }
            }
        }
    }

    private suspend fun createUser(user: FirebaseUser) {
        userRepository.createUser(user)
    }

    private suspend fun googleOneTapSignIn(context: Context, onSignInComplete: (Boolean) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val request = createGoogleIdRequest(true)
                val result = credentialManager.getCredential(context, request)
                Log.d("SignedIn", "The user already signed in!!!")
                handleSignInResult(result, onSignInComplete)
            } catch (e: GetCredentialException) {
                Log.d("SignedIn", "The user has not signed in yet!!!")
                try {
                    val request = createGoogleIdRequest(false)
                    val result = credentialManager.getCredential(context, request)
                    handleSignInResult(result, onSignInComplete)
                } catch (e: GetCredentialException) {
                    Log.d("SignedIn", "Error during sign in.")
                    onSignInComplete(false)
                }
            }
        }
    }

    private fun createAccessWithGoogleIdRequest(): GetCredentialRequest {
        val nonce = generateNonce()
        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID)
            .setNonce(nonce)
            .build()
        return GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()
    }

    private fun createGoogleIdRequest(filterByAuthorizedAccounts: Boolean): GetCredentialRequest {
        val nonce = generateNonce()
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts)
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(false)
            .setNonce(nonce)
            .build()
        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    private fun generateNonce(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return String(CharArray(16) { allowedChars.random() })
    }

    private fun handleSignInResult(
        result: GetCredentialResponse,
        onSignInComplete: (Boolean) -> Unit
    ) {
        val credential = result.credential
        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                Log.d("Credentials", "User successfully signed in!")
                firebaseAuthWithGoogle(googleIdTokenCredential.idToken, onSignInComplete)
            } catch (e: GoogleIdTokenParsingException) {
                Log.e("Credentials", "Invalid Google ID Token response", e)
                onSignInComplete(false)
            }
        } else {
            Log.e("Credentials", "Unexpected type of credential")
            onSignInComplete(false)
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
        _uiState.update { it.copy(isUserSignedIn = false) }
    }

    fun deleteUser(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val user = authRepository.getCurrentUser()
                if (user != null) {
                    val uid = user.uid
                    val success = authRepository.deleteUser()
                    if (success) {
                        userRepository.deleteUser(uid)
                        _uiState.update { it.copy(isUserSignedIn = false, isLoading = false) }
                    } else {
                        _uiState.update {
                            it.copy(
                                requiresReauthentication = true,
                                isLoading = false
                            )
                        }
                        reauthenticateAndDeleteUser(context)
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Error al obtener el usuario actual"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error deleting user", e)
                _uiState.update {
                    it.copy(
                        errorMessage = "Error al borrar la cuenta.",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun reauthenticateAndDeleteUser(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val request = createGoogleIdRequest(false) // Obtener token de Google
                val result = credentialManager.getCredential(context, request)
                if (result.credential is CustomCredential) {
                    val customCredential = result.credential as CustomCredential
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(customCredential.data)
                    val idToken = googleIdTokenCredential.idToken

                    val success = authRepository.reauthenticateWithGoogle(idToken) // Re-autenticar
                    if (success) {
                        deleteUser(context) // Intentar eliminar de nuevo
                    } else {
                        _uiState.update {
                            it.copy(
                                errorMessage = "Error al re-autenticar.",
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error during reauthentication", e)
                _uiState.update {
                    it.copy(
                        errorMessage = "Error al re-autenticar.",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun showErrorDialog(show: Boolean) {
        _uiState.update { it.copy(showErrorDialog = show) }
    }
}
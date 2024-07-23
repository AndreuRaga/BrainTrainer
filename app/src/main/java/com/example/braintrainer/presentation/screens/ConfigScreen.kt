package com.example.braintrainer.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.ViewModels.AuthViewModel
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun ConfigScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val uiState by authViewModel.uiState.collectAsState()

    if (uiState.showErrorDialog) {
        ErrorAlertDialog(errorMessage = uiState.errorMessage ?: "Error desconocido") {
            authViewModel.showErrorDialog(false)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            authViewModel.deleteUser()
            Log.d("ConfigScreen", "Pantalla de inicio")
        }) {
            Text("Borrar cuenta")
        }
        Button(onClick = {
            authViewModel.signOut()
            navController.navigate(AppScreens.AuthScreen.route) {
                popUpTo(AppScreens.AuthScreen.route) { inclusive = true }
            }
        }) {
            Text("Cerrar sesión")
        }
        LaunchedEffect(uiState.isUserSignedIn) {
            if (!uiState.isUserSignedIn) {
                navController.navigate(AppScreens.AuthScreen.route) {
                    popUpTo(AppScreens.AuthScreen.route) { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun ErrorAlertDialog(errorMessage: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Error") },
        text = { Text(errorMessage) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Aceptar")
            }
        }
    )
}
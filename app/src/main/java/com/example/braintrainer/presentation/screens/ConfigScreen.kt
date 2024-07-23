package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.ViewModels.ConfigViewModel
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun ConfigScreen(navController: NavHostController, configViewModel: ConfigViewModel) {
    val uiState by configViewModel.uiState.collectAsState()

    if (uiState.showErrorDialog) {
        ErrorAlertDialog(errorMessage = uiState.errorMessage ?: "Error desconocido") {
            configViewModel.showErrorDialog(false)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { configViewModel.deleteUser() }) {
            Text("Borrar cuenta")
        }
        Button(onClick = {
            configViewModel.signOut()
            navController.navigate(AppScreens.AuthScreen.route) {
                popUpTo(AppScreens.AuthScreen.route) { inclusive = true }
            }
        }) {
            Text("Cerrar sesión")
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
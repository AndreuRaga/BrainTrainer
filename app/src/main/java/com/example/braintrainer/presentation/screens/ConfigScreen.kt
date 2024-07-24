package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
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
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        uiState.profilePictureUrl?.let {
            AsyncImage(
                model = it,
                contentDescription = "Foto de perfil",
                modifier = Modifier.size(100.dp).clip(CircleShape)
            )
        }
        uiState.userName?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        uiState.userEmail?.let {
            Text(
                text = it,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Button(
            onClick = { authViewModel.deleteUser() },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(1.dp)) // Bordes circulares
        ) {
            Text("Borrar cuenta")
        }
        Button(
            onClick = { authViewModel.signOut() },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(1.dp)), // Bordes circulares
        ) {
            Text("Cerrar sesión")
        }
        // Navegación después de cerrar sesión o borrar cuenta
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
package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.braintrainer.presentation.ViewModels.AuthViewModel
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun ConfigScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val uiState by authViewModel.uiState.collectAsState()

    if (uiState.showDialog) {
        ReauthDialog(
            errorPassword = uiState.showErrorDialog,
            onConfirm = { password ->
                authViewModel.reauthUser(password)
            }
        ) { authViewModel.showDialog(false) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Margen general
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        uiState.profilePictureUrl?.let {
            AsyncImage(
                model = it,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre la foto y el nombre
        uiState.userName?.let {
            Text(text = it,
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
        Spacer(modifier = Modifier.height(50.dp)) // Espacio entre el email y los botones
        Button(
            onClick = { authViewModel.deleteUser() },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RectangleShape
        ) {
            Text("Borrar cuenta")
        }
        Button(
            onClick = { authViewModel.signOut() },
            modifier = Modifier
                .fillMaxWidth(), // Bordes circulares
            shape = RectangleShape
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
fun ReauthDialog(errorPassword: Boolean, onConfirm: (String) -> Unit, onDismiss: () -> Unit) {
    var password by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¡Atención!") },
        text = {
            Column {
                Text("Ten en cuenta que si borras tu cuenta se perderá todo tu pogreso en Brain Trainer.")
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Introduce tu contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    supportingText = { if (errorPassword) Text("Contraseña incorrecta.") },
                    isError = errorPassword
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(password) }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
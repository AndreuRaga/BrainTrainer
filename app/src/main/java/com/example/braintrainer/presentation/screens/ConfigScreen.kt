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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.braintrainer.presentation.ViewModels.AuthViewModel
import com.example.braintrainer.presentation.navigation.AppScreens
import com.example.braintrainer.presentation.uiStates.AuthUiState

@Composable
fun ConfigScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val uiState by authViewModel.uiState.collectAsState()

    // Mostrar diálogo de re-autenticación si es necesario
    if (uiState.showDialog) {
        ReauthDialog(
            errorPassword = uiState.showErrorDialog,
            onConfirm = { password -> authViewModel.reauthUser(password) },
            onDismiss = { authViewModel.showDialog(false) }
        )
    }
    Scaffold(bottomBar = { BottomBarMenu(navController) }) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding).padding(16.dp), // Margen general
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileInfoSection(uiState)
            Spacer(modifier = Modifier.height(32.dp)) // Espacio entre el email y los botones
            AccountActionsSection(authViewModel)
            NavigateOnSignOut(uiState, navController)
        }
    }
}

@Composable
fun ProfileInfoSection(uiState: AuthUiState) {
    uiState.profilePictureUrl?.let {
        AsyncImage(
            model = it,
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
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
}

@Composable
fun AccountActionsSection(authViewModel: AuthViewModel) {
    Button(
        onClick = { authViewModel.deleteUser() },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red // Cambiar color del botón
        )
    ) {
        Text("Borrar cuenta")
    }
    Button(
        onClick = { authViewModel.signOut() },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text("Cerrar sesión")
    }
}

@Composable
fun NavigateOnSignOut(uiState: AuthUiState, navController: NavHostController) {
    LaunchedEffect(uiState.isUserSignedIn) {
        if (!uiState.isUserSignedIn) {
            navController.navigate(AppScreens.AuthScreen.route) {
                popUpTo(AppScreens.AuthScreen.route) { inclusive = true }
            }
        }
    }
}

@Composable
fun ReauthDialog(errorPassword: Boolean, onConfirm: (String) -> Unit, onDismiss: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¡Atención!", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Ten en cuenta que si borras tu cuenta se perderá todo tu pogreso en Brain Trainer.")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Introduce tu contraseña") },
                    visualTransformation = visualTransformation,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    supportingText = { if (errorPassword) Text("Contraseña incorrecta.", color = Color.Red) },
                    isError = errorPassword,
                    trailingIcon =  {
                        PasswordVisibilityToggle(passwordVisible) { passwordVisible = !it }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(password) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cancelar")
            }
        },
        shape = RoundedCornerShape(8.dp),
        containerColor = MaterialTheme.colorScheme.surface
    )
}

@Composable
fun PasswordVisibilityToggle(isVisible: Boolean, onToggle: (Boolean) -> Unit) {
    val image = if (isVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
    val description = if (isVisible) "Hide password" else "Show password"

    IconButton(onClick = { onToggle(isVisible) }) {
        Icon(imageVector = image, contentDescription = description)
    }
}
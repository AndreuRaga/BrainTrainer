package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.braintrainer.R
import com.example.braintrainer.presentation.ViewModels.AuthViewModel
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun AuthScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val uiState by authViewModel.uiState.collectAsState()

    // Manejo de navegación basado en el estado del usuario
    LaunchedEffect(uiState.isUserSignedIn) {
        if (uiState.isUserSignedIn) {
            navController.navigate(AppScreens.ConfigScreen.route)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "¡Bienvenido/a a Brain Trainer!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¿Ya tienes cuenta?")
        Button(onClick = { authViewModel.handleGoogleSignIn(context) }) {
            Text("Inicia sesión")
        }
        Text("¿No tienes cuenta?")
        Button(onClick = { /* Acción al hacer clic */ }) {
            Text("Regístrate con Google")
        }
        // Mostrar diálogo de error si es necesario
        if (uiState.showErrorDialog) {
            AlertDialog(
                onDismissRequest = { authViewModel.showErrorDialog(false) },
                title = { Text("Error") },
                text = { Text(uiState.errorMessage ?: "Error desconocido") },
                confirmButton = {
                    Button(onClick = { authViewModel.showErrorDialog(false) }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    //AuthScreen()
}
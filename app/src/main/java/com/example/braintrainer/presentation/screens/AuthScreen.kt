package com.example.braintrainer.presentation.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ButtonDefaults
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

    // Mostrar diálogo de error si es necesario
    if (uiState.showErrorDialog) {
        ErrorDialog(
            errorMessage = uiState.errorMessage ?: "Error desconocido",
            onDismiss = { authViewModel.showErrorDialog(false) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WelcomeText()
        AppIcon()
        Spacer(modifier = Modifier.height(50.dp))
        SignInSection(authViewModel, context)
        Spacer(modifier = Modifier.height(16.dp))
        SignUpSection()
    }
}

@Composable
fun WelcomeText() {
    Text(
        text = "¡Bienvenido/a a Brain Trainer!",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
fun AppIcon() {
    Image(
        painter = painterResource(id = R.drawable.app_icon), // Reemplaza con tu icono
        contentDescription = "Logo",
        modifier = Modifier.size(150.dp)
    )
}

@Composable
fun SignInSection(authViewModel: AuthViewModel, context: Context) {
    Text("¿Ya tienes cuenta?")
    GoogleSignInButton(authViewModel, context)
}

@Composable
fun GoogleSignInButton(authViewModel: AuthViewModel, context: Context) {
    Button(
        onClick = { authViewModel.handleGoogleSignIn(context) },
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_icon),
            contentDescription = "Google Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text("Inicia sesión")
    }
}

@Composable
fun SignUpSection() {
    Text("¿No tienes cuenta?")
    Button(onClick = { /* Acción al hacer clic */ }) {
        Text("Regístrate con Google")
    }
}

@Composable
fun ErrorDialog(errorMessage: String, onDismiss: () -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    //AuthScreen()
}
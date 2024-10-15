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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
    val context = LocalContext.current
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }

    Scaffold(bottomBar = { BottomBarMenu(navController) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileInfo(uiState)
            Spacer(modifier = Modifier.height(32.dp))
            DeleteAccountButton(onDeleteClicked = { showDeleteConfirmationDialog = true })
            SignOutButton(onSignOutClicked = { authViewModel.signOut() })
        }
    }

    if (showDeleteConfirmationDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                authViewModel.deleteUser(context)
                showDeleteConfirmationDialog = false
            },
            onDismiss = { showDeleteConfirmationDialog = false }
        )
    }

    NavigateOnSignOut(uiState, navController)
}

@Composable
fun ProfileInfo(uiState: AuthUiState) {
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
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
    uiState.userEmail?.let {
        Text(
            text = it,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun DeleteAccountButton(onDeleteClicked: () -> Unit) {
    Button(
        onClick = onDeleteClicked,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        )
    ) {
        Text(
            text = "Borrar cuenta",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SignOutButton(onSignOutClicked: () -> Unit) {
    Button(
        onClick = onSignOutClicked,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF428BCA)
        )
    ) {
        Text("Cerrar sesión",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DeleteConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¡Atención!") },
        text = { Text("¿Estás seguro de que quieres borrar tu cuenta? Ten en cuenta que perderás todo tu pogreso en Brain Trainer.") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Sí")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("No")
            }
        }
    )
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
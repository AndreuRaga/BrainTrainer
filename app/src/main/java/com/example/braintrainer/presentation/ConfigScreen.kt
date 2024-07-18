package com.example.braintrainer.presentation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.navigation.AppScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ConfigScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var showErrorDialog by remember { mutableStateOf<String?>(null) }

    if (showErrorDialog != null) {
        ErrorAlertDialog(errorMessage = showErrorDialog!!) {
            showErrorDialog = null
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            scope.launch {
                deleteUser(context, navController){ errorMessage ->
                    showErrorDialog = errorMessage
                }
            }
        }) {
            Text("Borrar cuenta")
        }
        Button(onClick = {
            signOut(context, navController)
        }) {
            Text("Cerrar sesión")
        }
    }
}

private fun signOut(context: Context, navController: NavHostController) {
    auth.signOut()
    // Navega de vuelta a la pantalla de autenticación
    navController.navigate(AppScreens.AuthScreen.route) {
        popUpTo(AppScreens.AuthScreen.route) { inclusive = true } // Limpia la pila de navegación
    }
}

private suspend fun deleteUser(context: Context, navController: NavHostController, onError: (String) -> Unit) {
    withContext(Dispatchers.IO) {
        try {
            auth.currentUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("User", "User account deleted.")
                        // Navega de vuelta a la pantalla de autenticación
                        navController.navigate(AppScreens.AuthScreen.route) {
                            popUpTo(AppScreens.AuthScreen.route) { inclusive = true }
                        }
                    } else {
                        onError("Error al borrar la cuenta: ${task.exception?.message}")
                    }
                }
        } catch (e: Exception) {
            onError("Error al borrar la cuenta: ${e.message}")
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
        })
}
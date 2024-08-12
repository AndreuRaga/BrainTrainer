package com.example.braintrainer.presentation.screens.gamesOpt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.ViewModels.PlayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(navController: NavHostController, playViewModel: PlayViewModel = hiltViewModel()) {
    val game = playViewModel.getGame()
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = game?.name ?: "Juego no encontrado",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (game?.id) {
                "add_sub" -> AddSubScreen(navController)
                else -> Text("Juego no disponible")
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("¡Atención!") },
                    text = { Text("¿Seguro de que quieres salir? Se perderán los puntos que tengas acumulados hasta este momento.") },
                    confirmButton = {
                        Button(onClick = {
                            navController.popBackStack()
                            showDialog = false
                        } ) {
                            Text("Sí")
                        }
                    },
                    dismissButton = { Button(onClick = { showDialog = false } ) {
                        Text("No")
                    } }
                )
            }
        }
    }
}
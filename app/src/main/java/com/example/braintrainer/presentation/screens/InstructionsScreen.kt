package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun InstructionsScreen(navController: NavHostController, gameName: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(gameName)
        Text("Instrucciones:")
        // Mostrar instrucciones del juego
        Text("Aquí van las instrucciones del juego.")
        Text("Mejores puntuaciones:")
        // Mostrar las 3 mejores puntuaciones
        Text("Puntuación 1")
        Text("Puntuación 2")
        Text("Puntuación 3")
        Button(onClick = { /* Acción para empezar a jugar */ }) {
            Text("Empezar a jugar")
        }
    }
}
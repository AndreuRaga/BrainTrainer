package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun InstructionsScreen(navController: NavHostController, gameName: String) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = gameName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Instrucciones:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Aquí van las instrucciones del juego.")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Mejores puntuaciones:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            Text("Puntuación 1")
            Text("Puntuación 2")
            Text("Puntuación 3")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { /* Acción para empezar a jugar */ },
            modifier = Modifier.width(200.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Jugar")
        }
    }
}
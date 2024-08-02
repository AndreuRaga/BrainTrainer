package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun GameStatsScreen(navController: NavHostController) {
    val gameCategories = listOf(
        GameCategory("Matemáticas", listOf("Suma", "Resta", "Multiplicación", "División")),
        GameCategory("Memoria", listOf("Cartas", "Números", "Secuencias")),
        GameCategory("Lógica", listOf("Sudokus", "Puzzles", "Acertijos"))
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(gameCategories) { category ->
            Text(
                text = category.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            category.games.forEach { game ->
                GameStatsItem(game)
            }
        }
    }
}

@Composable
fun GameStatsItem(gameName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = gameName,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("80%") // Reemplazar con la puntuación promedio real
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = {
                0.8f // Reemplazar con el progreso real
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
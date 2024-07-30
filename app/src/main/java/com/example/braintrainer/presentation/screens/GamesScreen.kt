package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun GamesScreen(navController: NavHostController) {
    val gameCategories = listOf(
        GameCategory("Matemáticas", listOf("Suma", "Resta", "Multiplicación", "División")),
        GameCategory("Memoria", listOf("Cartas", "Números", "Secuencias")),
        GameCategory("Lógica", listOf("Sudokus", "Puzzles", "Acertijos"))
    )

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(gameCategories) { category ->
            Text(
                text = category.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            category.games.forEach { game ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        navController.navigate(AppScreens.InstructionsScreen.route + "/$game")
                    }
                ) {
                    Text(
                        text = game,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

data class GameCategory(val name: String, val games: List<String>)
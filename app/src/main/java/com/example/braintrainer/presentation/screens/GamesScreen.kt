package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                style = MaterialTheme.typography.headlineMedium
            )
            category.games.forEach { game ->
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    onClick = { navController.navigate(AppScreens.InstructionsScreen.route + "/$game") }) {
                    Text(game)
                }
            }
        }
    }
}

data class GameCategory(val name: String, val games: List<String>)
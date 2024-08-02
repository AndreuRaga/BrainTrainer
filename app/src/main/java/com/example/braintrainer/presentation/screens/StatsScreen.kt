package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun StatsScreen(navController: NavHostController) {
    val gameCategories = listOf(
        GameCategory("Matemáticas", listOf("Suma", "Resta", "Multiplicación", "División")),
        GameCategory("Memoria", listOf("Cartas", "Números", "Secuencias")),
        GameCategory("Lógica", listOf("Sudokus", "Puzzles", "Acertijos"))
    )
    Scaffold(bottomBar = { BottomBarMenu(navController) }) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Text("Estadísticas")
        }
    }
}
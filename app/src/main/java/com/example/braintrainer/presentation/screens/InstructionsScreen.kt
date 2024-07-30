package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun InstructionsScreen(navController: NavHostController) {
    Column {
        Text("Instrucciones del juego")
    }
}
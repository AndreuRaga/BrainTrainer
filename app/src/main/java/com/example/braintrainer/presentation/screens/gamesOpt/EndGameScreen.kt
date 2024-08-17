package com.example.braintrainer.presentation.screens.gamesOpt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.ViewModels.EndGameViewModel
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun EndGameScreen(navController: NavHostController, gameId: String, points: Int, endGameViewModel: EndGameViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(gameId)
        Text(
            text = "Mejores puntuaciones:",
            style = MaterialTheme.typography.headlineSmall
        )
        Text("1. $points puntos")
        Text("2. __ puntos")
        Text("3. __ puntos")
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "¡Enhorabuena!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(AppScreens.GamesScreen.route) }) {
            Text("Jugar a otros juegos")
        }
    }
}
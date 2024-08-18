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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.ViewModels.EndGameViewModel
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun EndGameScreen(navController: NavHostController, endGameViewModel: EndGameViewModel = hiltViewModel()) {
    val uiState = endGameViewModel.uiState.collectAsState()
    val points = uiState.value.currentPoints
    val bestScore = uiState.value.bestScore
    val isNewRecord = uiState.value.isNewRecord

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mejor puntuación:",
            style = MaterialTheme.typography.headlineSmall
        )
        Text("$bestScore punto(s)") // Mostrar la mejor puntuación
        Text(
            text = "Puntuación obtenida:",
            style = MaterialTheme.typography.headlineSmall
        )
        Text("$points punto(s)") // Mostrar la puntuación actual

        Spacer(modifier = Modifier.height(32.dp))
        if (isNewRecord) {
            Text(
                text = "¡Enhorabuena!\n" + "Has establecido un nuevo récord.",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "No te preocupes.\n" + "Siempre hay margen de mejora.",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(AppScreens.GamesScreen.route) }) {
            Text("Jugar a otros juegos")
        }
    }
}
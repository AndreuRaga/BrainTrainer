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
        ScoreSection(bestScore = bestScore, points = points)
        Spacer(modifier = Modifier.height(32.dp))
        ResultMessage(isNewRecord = isNewRecord)
        Spacer(modifier = Modifier.height(16.dp))
        PlayOtherGamesButton(navController = navController)
    }
}

@Composable
fun ScoreSection(bestScore: Int, points: Int) {
    Column {
        Text(
            text = "Mejor puntuación:",
            style = MaterialTheme.typography.headlineSmall
        )
        Text("$bestScore punto(s)")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Puntuación obtenida:",
            style = MaterialTheme.typography.headlineSmall
        )
        Text("$points punto(s)")
    }
}

@Composable
fun ResultMessage(isNewRecord: Boolean) {
    val message = if (isNewRecord) {
        "¡Enhorabuena!\nHas establecido un nuevo récord."
    } else {
        "No te preocupes.\nSiempre hay margen de mejora."
    }

    Text(
        text = message,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun PlayOtherGamesButton(navController: NavHostController) {
    Button(onClick = { navController.navigate(AppScreens.GamesScreen.route) }) {
        Text("Jugar a otros juegos")
    }
}
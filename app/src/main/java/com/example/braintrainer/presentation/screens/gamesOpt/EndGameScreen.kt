package com.example.braintrainer.presentation.screens.gamesOpt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.ViewModels.EndGameViewModel
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun EndGameScreen(
    navController: NavHostController,
    endGameViewModel: EndGameViewModel = hiltViewModel()
) {
    val uiState = endGameViewModel.uiState.collectAsState()
    val points = uiState.value.currentPoints
    val bestScore = uiState.value.bestScore
    val isNewRecord = uiState.value.isNewRecord

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScoreSection(bestScore = bestScore, points = points)
            Spacer(modifier = Modifier.height(32.dp))
            ResultMessage(isNewRecord = isNewRecord)
            Spacer(modifier = Modifier.height(32.dp))
            PlayOtherGamesButton(navController = navController)
        }
    }
}

@Composable
fun ScoreSection(bestScore: Int, points: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreColumn("Mejor puntuación", bestScore)
            Spacer(modifier = Modifier.width(16.dp))
            ScoreColumn("Puntuación obtenida", points)
        }
    }
}

@Composable
fun ScoreColumn(title: String, score: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$score punto(s)",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun PlayOtherGamesButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate(AppScreens.GamesScreen.route) },
        modifier = Modifier.width(200.dp)
    ) {
        Text("Jugar a otros juegos")
    }
}
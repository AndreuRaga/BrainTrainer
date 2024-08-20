package com.example.braintrainer.presentation.screens.statsOpt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.data.models.Game
import com.example.braintrainer.data.models.GameCategory
import com.example.braintrainer.presentation.ViewModels.GameStatsViewModel
import com.example.braintrainer.presentation.uiStates.StatsUiState

@Composable
fun GameStatsScreen(
    navController: NavHostController,
    gameStatsViewModel: GameStatsViewModel = hiltViewModel()
) {
    val uiState = gameStatsViewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is StatsUiState.Loading -> LoadingScreen()
        is StatsUiState.Success -> GameStatsList(state.categories)
        is StatsUiState.Error -> ErrorScreen(state.message)
    }
}

@Composable
fun GameStatsList(categories: List<GameCategory>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category)
        }
    }
}

@Composable
fun CategoryItem(category: GameCategory) {
    Column {
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

@Composable
fun GameStatsItem(game: Game) {
    val progress = if (game.maxScore > 0) {
        game.bestScore?.toFloat()?.div(game.maxScore) ?: 0f
    } else {
        0f
    }

    StatItem(
        title = game.name,
        score = game.bestScore.toString(),
        performance = getPerformance(progress),
        progress = progress
    )
}
package com.example.braintrainer.presentation.screens.statsOpt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.data.models.GameCategory
import com.example.braintrainer.presentation.ViewModels.GeneralStatsViewModel
import com.example.braintrainer.presentation.uiStates.OverallPerformance
import com.example.braintrainer.presentation.uiStates.StatsUiState

@Composable
fun GeneralStatsScreen(
    navController: NavHostController,
    generalStatsViewModel: GeneralStatsViewModel = hiltViewModel()
) {
    val uiState = generalStatsViewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is StatsUiState.Loading -> LoadingScreen()
        is StatsUiState.Success -> StatsContent(state)
        is StatsUiState.Error -> ErrorScreen(state.message)
    }
}

@Composable
fun StatsContent(state: StatsUiState.Success) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(state.categories) { category ->
            CategoryStatsItem(category)
        }
        item {
            state.overallPerformance?.let {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                OverallPerformanceItem(it)
            }
        }
    }
}

@Composable
fun CategoryStatsItem(category: GameCategory) {
    category.progress?.let { getPerformance(it) }?.let {
        StatItem(
            title = category.name,
            score = category.totalBestScore.toString(),
            performance = it,
            progress = category.progress
        )
    }
}

@Composable
fun OverallPerformanceItem(overallPerformance: OverallPerformance) {
    StatItem(
        title = "Rendimiento general",
        score = overallPerformance.totalBestScore.toString(),
        performance = getPerformance(overallPerformance.progress),
        progress = overallPerformance.progress
    )
}

@Composable
fun StatItem(title: String, score: String, performance: String, progress: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = title)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("$score punto(s)")
            Text("Rendimiento: $performance")
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private fun getPerformance(progress: Float): String {
    return when {
        progress < 0.25f -> "Bajo"
        progress < 0.5f -> "Medio-bajo"
        progress < 0.75f -> "Medio-alto"
        else -> "Alto"
    }
}
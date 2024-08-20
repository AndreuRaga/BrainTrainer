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
import com.example.braintrainer.presentation.ViewModels.GeneralStatsUiState
import com.example.braintrainer.presentation.ViewModels.GeneralStatsViewModel

@Composable
fun GeneralStatsScreen(navController: NavHostController, generalStatsViewModel: GeneralStatsViewModel = hiltViewModel()) {
    val uiState = generalStatsViewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is GeneralStatsUiState.Loading -> {
            Text("Cargando estadísticas...")
        }
        is GeneralStatsUiState.Success -> {
            GeneralStatsList(state.categories)
        }
        is GeneralStatsUiState.Error -> {
            Text("Error: ${state.message}")
        }
    }
}

@Composable
fun GeneralStatsList(categories: List<GameCategory>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(categories) { category ->
            CategoryStatsItem(category)
        }
    }
}

@Composable
fun CategoryStatsItem(category: GameCategory) {
    val performance = when {
        category.progress!! < 0.25f -> "Bajo"
        category.progress < 0.5f -> "Medio-bajo"
        category.progress < 0.75f -> "Medio-alto"
        else -> "Alto"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = category.name)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("${category.totalBestScore} punto(s)")
            Text("Rendimiento: $performance")
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { category.progress },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
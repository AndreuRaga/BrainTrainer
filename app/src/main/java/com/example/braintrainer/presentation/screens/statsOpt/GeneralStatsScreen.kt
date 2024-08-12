package com.example.braintrainer.presentation.screens.statsOpt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.data.models.GameCategoryR
import com.example.braintrainer.presentation.ViewModels.GeneralStatsViewModel

@Composable
fun GeneralStatsScreen(navController: NavHostController, generalStatsViewModel: GeneralStatsViewModel = hiltViewModel()) {
    val gameCategories = generalStatsViewModel.getGameCategories()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(gameCategories) { category ->
            CategoryStatsItem(category.name)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            CategoryStatsItem("Rendimiento general")
        }
    }
}

@Composable
fun CategoryStatsItem(categoryName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryName,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("80%") // Reemplazar con la puntuación promedio real
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = {
                0.8f // Reemplazar con el progreso real
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
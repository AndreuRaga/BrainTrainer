package com.example.braintrainer.presentation.screens.statsOpt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.navigation.AppScreens
import com.example.braintrainer.presentation.screens.BottomBarMenu

@Composable
fun StatsScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(AppScreens.GeneralStatsScreen, AppScreens.GameStatsScreen)

    Scaffold(
        bottomBar = { BottomBarMenu(navController) },
        topBar = {
            StatsTabs(tabs, selectedTabIndex) { index ->
                selectedTabIndex = index
            }
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            when (selectedTabIndex) {
                0 -> GeneralStatsScreen(navController)
                1 -> GameStatsScreen(navController)
            }
        }
    }
}

@Composable
fun StatsTabs(tabs: List<AppScreens>, selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    TabRow(
        modifier = Modifier.padding(16.dp),
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent, // Eliminar el color de fondo por defecto
        contentColor = Color(0xFF495D92), // Cambiar el color de las tabs
        indicator = { tabPositions ->
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = Color(0xFF495D92) // Cambiar el color del indicador
            )
        }
    ) {
        tabs.forEachIndexed { index, screen ->
            Tab(
                text = { Text(text = screen.title!!, color = Color(0xFF495D92), fontWeight = FontWeight.Bold) },
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Text(text = "Cargando estadísticas...", modifier = Modifier.padding(16.dp))
}

@Composable
fun ErrorScreen(message: String) {
    Text(text = "Error: $message", modifier = Modifier.padding(16.dp))
}

@Composable
fun StatItem(title: String, score: String, performance: String, progress: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = title, color = Color.Black, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "$score punto(s)", color = Color.Black, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Rendimiento: $performance", color = Color.Black, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF495D92),
                trackColor = Color.White
            )
        }
    }
}

fun getPerformance(progress: Float): String {
    return when {
        progress < 0.25f -> "Bajo"
        progress < 0.5f -> "Medio-bajo"
        progress < 0.75f -> "Medio-alto"
        else -> "Alto"
    }
}
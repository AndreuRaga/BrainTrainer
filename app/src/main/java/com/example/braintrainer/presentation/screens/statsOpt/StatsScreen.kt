package com.example.braintrainer.presentation.screens.statsOpt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.navigation.AppScreens
import com.example.braintrainer.presentation.screens.BottomBarMenu

@Composable
fun StatsScreen(navController: NavHostController) {
    var selectedTabIndex by remember {mutableIntStateOf(0) }
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
        selectedTabIndex = selectedTabIndex
    ) { tabs.forEachIndexed { index, screen ->
        Tab(
            text = { Text(screen.title!!, fontWeight = FontWeight.Bold) },
            selected = selectedTabIndex == index,
            onClick = { onTabSelected(index) }
        )
    } }
}

@Composable
fun LoadingScreen() {
    Text("Cargando estadísticas...")
}

@Composable
fun ErrorScreen(message: String) {
    Text("Error: $message")
}
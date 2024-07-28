package com.example.braintrainer.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreens(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object AuthScreen : AppScreens("auth_screen")
    object BottomBarMenu : AppScreens("bottom_bar_menu")
    object GamesScreen : AppScreens("games_screen", "Juegos", Icons.Default.Games)
    object StatsScreen : AppScreens("stats_screen", "Estadísticas", Icons.Default.QueryStats)
    object ConfigScreen : AppScreens("config_screen", "Configuración", Icons.Default.Settings)
}
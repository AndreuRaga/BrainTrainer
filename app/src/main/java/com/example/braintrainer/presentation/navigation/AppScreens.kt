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
    object GamesScreen : AppScreens("games_screen", "Juegos", Icons.Default.Games)
    object InstructionsScreen : AppScreens("instructions_screen")
    object PlayScreen : AppScreens("play_screen")
    object AddSubScreen : AppScreens("add_sub_screen")
    object EndGameScreen : AppScreens("end_game_screen") {
        fun passData(gameId: String, points: Int) = "end_game_screen/$gameId/$points"
    }
    object StatsScreen : AppScreens("stats_screen", "Estadísticas", Icons.Default.QueryStats)
    object GeneralStatsScreen : AppScreens("general_stats_screen", "Generales")
    object GameStatsScreen : AppScreens("game_stats_screen", "Por juego")
    object ConfigScreen : AppScreens("config_screen", "Configuración", Icons.Default.Settings)
}
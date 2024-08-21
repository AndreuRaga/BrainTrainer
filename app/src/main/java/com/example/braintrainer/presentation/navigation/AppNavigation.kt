package com.example.braintrainer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.braintrainer.presentation.ViewModels.AuthViewModel
import com.example.braintrainer.presentation.screens.AuthScreen
import com.example.braintrainer.presentation.screens.ConfigScreen
import com.example.braintrainer.presentation.screens.gamesOpt.AddSubScreen
import com.example.braintrainer.presentation.screens.gamesOpt.CardsScreen
import com.example.braintrainer.presentation.screens.gamesOpt.EndGameScreen
import com.example.braintrainer.presentation.screens.gamesOpt.GamesScreen
import com.example.braintrainer.presentation.screens.gamesOpt.InstructionsScreen
import com.example.braintrainer.presentation.screens.gamesOpt.PlayScreen
import com.example.braintrainer.presentation.screens.statsOpt.GameStatsScreen
import com.example.braintrainer.presentation.screens.statsOpt.GeneralStatsScreen
import com.example.braintrainer.presentation.screens.statsOpt.StatsScreen

@Composable
fun AppNavigation() {
    val authViewModel: AuthViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.AuthScreen.route
    ) {
        composable(AppScreens.AuthScreen.route) {
            AuthScreen(navController, authViewModel)
        }
        composable(AppScreens.GamesScreen.route) {
            GamesScreen(navController)
        }
        composable(
            route = AppScreens.InstructionsScreen.route + "/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.StringType })
        ) {
            InstructionsScreen(navController)
        }
        composable(
            route = AppScreens.PlayScreen.route + "/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.StringType })
        ) {
            PlayScreen(navController)
        }
        composable(AppScreens.AddSubScreen.route) {
            AddSubScreen(navController)
        }
        composable(AppScreens.CardsScreen.route) {
            CardsScreen(navController)
        }
        composable(
            route = AppScreens.EndGameScreen.route + "/{gameId}/{points}",
            arguments = listOf(
                navArgument("gameId") { type = NavType.StringType },
                navArgument("points") { type = NavType.IntType }
            )
        ) {
            EndGameScreen(navController)
        }
        composable(AppScreens.StatsScreen.route) {
            StatsScreen(navController)
        }
        composable(AppScreens.GeneralStatsScreen.route) {
            GeneralStatsScreen(navController)
        }
        composable(AppScreens.GameStatsScreen.route) {
            GameStatsScreen(navController)
        }
        composable(AppScreens.ConfigScreen.route) {
            ConfigScreen(navController, authViewModel)
        }
    }
}
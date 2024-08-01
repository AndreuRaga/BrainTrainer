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
import com.example.braintrainer.presentation.screens.EndGameScreen
import com.example.braintrainer.presentation.screens.GamesScreen
import com.example.braintrainer.presentation.screens.InstructionsScreen
import com.example.braintrainer.presentation.screens.PlayScreen
import com.example.braintrainer.presentation.screens.StatsScreen

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
            AppScreens.InstructionsScreen.route + "/{gameName}",
            arguments = listOf(navArgument("gameName") { type = NavType.StringType })
        ) {
            backStackEntry ->
            InstructionsScreen(navController, backStackEntry.arguments?.getString("gameName") ?: "")
        }
        composable(
            AppScreens.PlayScreen.route + "/{gameName}",
            arguments = listOf(navArgument("gameName") { type = NavType.StringType })) {
            backStackEntry ->
            PlayScreen(navController, backStackEntry.arguments?.getString("gameName") ?: "")
        }
        composable(AppScreens.EndGameScreen.route) {
            EndGameScreen(navController)
        }
        composable(AppScreens.StatsScreen.route) {
            StatsScreen(navController)
        }
        composable(AppScreens.ConfigScreen.route) {
            ConfigScreen(navController, authViewModel)
        }
    }
}
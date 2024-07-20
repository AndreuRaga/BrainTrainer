package com.example.braintrainer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.braintrainer.presentation.AuthScreen
import com.example.braintrainer.presentation.ConfigScreen
import com.example.braintrainer.presentation.ViewModels.AuthViewModel
import com.example.braintrainer.presentation.ViewModels.ConfigViewModel

@Composable
fun AppNavigation() {
    val authViewModel: AuthViewModel = viewModel()
    val configViewModel: ConfigViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.AuthScreen.route
    ) {
        composable(AppScreens.AuthScreen.route) {
            AuthScreen(navController, authViewModel)
        }
        composable(AppScreens.ConfigScreen.route) {
            ConfigScreen(navController, configViewModel)
        }
    }
}
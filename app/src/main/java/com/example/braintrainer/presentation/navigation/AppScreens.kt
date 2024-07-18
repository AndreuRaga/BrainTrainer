package com.example.braintrainer.presentation.navigation

sealed class AppScreens(val route: String) {
    object AuthScreen : AppScreens("auth_screen")
    object ConfigScreen : AppScreens("config_screen")
}
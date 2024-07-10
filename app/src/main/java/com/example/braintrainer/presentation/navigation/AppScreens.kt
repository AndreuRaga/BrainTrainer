package com.example.braintrainer.presentation.navigation

sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object AuthScreen : AppScreens("auth_screen")
    object ConfigScreen : AppScreens("config_screen")
}
package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun BottomBarMenu(navController: NavHostController) {
    val items = listOf(AppScreens.GamesScreen, AppScreens.StatsScreen, AppScreens.ConfigScreen)
    NavigationBar(containerColor = Color(0xFF495D92)) {
        items.forEach { screen ->
            NavigationBarItem(
                selected = navController.currentDestination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = screen.icon!!,
                            contentDescription = screen.title,
                            tint = if (navController.currentDestination?.route == screen.route) {
                                Color(0xFF495D92) // Color del icono seleccionado
                            } else {
                                Color.White // Color del icono no seleccionado
                            }
                        )
                        Text(
                            text = screen.title ?: "",
                            color = if (navController.currentDestination?.route == screen.route) {
                                Color(0xFF495D92) // Color del texto seleccionado
                            } else {
                                Color.White // Color del texto no seleccionado
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.White)
            )
        }
    }
}
package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun TopBarMenu(navController: NavHostController) {
    val items = listOf(AppScreens.GeneralStatsScreen, AppScreens.GameStatsScreen)
    NavigationBar(containerColor =  Color.Transparent) {
        items.forEach { screen ->
            NavigationBarItem(
                selected = navController.currentDestination?.route == screen.route,
                onClick = { navController.navigate(screen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                } },
                icon = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = screen.title ?: "",
                            color = if (navController.currentDestination?.route == screen.route) {
                                MaterialTheme.colorScheme.primary // Color del texto seleccionado
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant // Color del texto no seleccionado
                            }
                        )
                    }
                }
            )
        }
    }
}
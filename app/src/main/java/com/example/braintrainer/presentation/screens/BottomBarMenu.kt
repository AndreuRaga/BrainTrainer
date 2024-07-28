package com.example.braintrainer.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun BottomBarMenu(navController: NavController) {
    val items = listOf(AppScreens.GamesScreen, AppScreens.StatsScreen, AppScreens.ConfigScreen)
    Scaffold(
        bottomBar = {
            BottomAppBar {
                items.forEach { screen ->
                    NavigationBarItem(
                        selected = navController.currentDestination?.route == screen.route,
                        onClick = { navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                        } },
                        icon = { Icon(imageVector = screen.icon!!, contentDescription = screen.title) },
                        label = { Text(text = screen.title ?: "") }
                    )
                }
            }
        }
    ) {
        it
    }

}

@Preview(showBackground = true)
@Composable
fun BottomBarMenuPreview() {
    Column {
        Text(text = "BottomBarMenu")
        BottomAppBar {

        }
    }
}
package com.example.braintrainer.presentation.screens

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun BottomBarMenu(navController: NavHostController) {
    val items = listOf(AppScreens.GamesScreen, AppScreens.StatsScreen, AppScreens.ConfigScreen)
    NavigationBar(containerColor =  MaterialTheme.colorScheme.surfaceVariant) {
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
                    Icon(
                        imageVector = screen.icon!!,
                        contentDescription = screen.title,
                        tint = if (navController.currentDestination?.route == screen.route) {
                            MaterialTheme.colorScheme.primary // Color del icono seleccionado
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant // Color del icono no seleccionado
                        }
                    )
                },
                label = {
                    Text(
                        text = screen.title ?: "",
                        color = if (navController.currentDestination?.route == screen.route) {
                            MaterialTheme.colorScheme.primary // Color del texto seleccionado
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant // Color del texto no seleccionado
                        }
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarMenuPreview() {

}
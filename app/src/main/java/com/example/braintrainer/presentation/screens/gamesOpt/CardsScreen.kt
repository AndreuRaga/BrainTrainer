package com.example.braintrainer.presentation.screens.gamesOpt

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.ViewModels.CardsViewModel

@Composable
fun CardsScreen(navController: NavHostController, cardsViewModel: CardsViewModel = hiltViewModel()) {
    val uiState = cardsViewModel.uiState.collectAsState()
    Text(text = "Cards Screen")
}
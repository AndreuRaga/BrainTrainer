package com.example.braintrainer.presentation.screens.gamesOpt

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.R
import com.example.braintrainer.presentation.ViewModels.CardsViewModel
import com.example.braintrainer.presentation.navigation.AppScreens
import com.example.braintrainer.presentation.uiStates.CardData

@Composable
fun CardsScreen(
    navController: NavHostController,
    cardsViewModel: CardsViewModel = hiltViewModel()
) {
    val uiState by cardsViewModel.uiState.collectAsState()
    val gameId = uiState.gameId
    val cards = uiState.cards
    val points = uiState.points
    val attempts = uiState.attempts
    val maxAttempts = uiState.maxAttempts

    LaunchedEffect(points, attempts) {
        if (points == cards.size / 2 || attempts == maxAttempts) {
            navController.navigate(AppScreens.EndGameScreen.route + "/$gameId/$points")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.timeLeft > 0) {
            Text("${uiState.timeLeft}")
        } else {
            Text("¡A jugar!")
        }
        ScoreBoard(points, attempts, maxAttempts)
        CardsGrid(cards, uiState.areCardsBlocked, cardsViewModel::onCardClicked)
    }
}

@Composable
fun ScoreBoard(points: Int, attempts: Int, maxAttempts: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Puntos: $points")
        Text("Intentos: $attempts/$maxAttempts")
    }
}

@Composable
fun CardsGrid(cards: List<CardData>, areCardsBlocked: Boolean, onCardClicked: (CardData) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cards) { card ->
            CardItem(card, areCardsBlocked, onCardClicked)
        }
    }
}

@Composable
fun CardItem(card: CardData, isBlocked: Boolean, onCardClicked: (CardData) -> Unit) {
    Card(
        modifier = Modifier.aspectRatio(1f),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Button(
            onClick = { onCardClicked(card) },
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            shape = RectangleShape,
            border = ButtonDefaults.outlinedButtonBorder,
            enabled = !isBlocked && !card.isRevealed // Deshabilitar el botón si la carta está bloqueada o si está revelada
        ) {
            Image(
                painter = painterResource(id = if (card.isRevealed) card.image else R.drawable.card_background),
                contentDescription = "Imagen que funciona como botón",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
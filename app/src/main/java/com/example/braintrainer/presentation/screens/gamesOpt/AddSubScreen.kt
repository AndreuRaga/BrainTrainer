package com.example.braintrainer.presentation.screens.gamesOpt

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.ViewModels.AddSubViewModel
import com.example.braintrainer.presentation.navigation.AppScreens

@Composable
fun AddSubScreen(
    navController: NavHostController,
    addSubViewModel: AddSubViewModel = hiltViewModel()
) {
    val uiState by addSubViewModel.uiState.collectAsState()
    val gameId = uiState.gameId
    val num1 = uiState.num1
    val num2 = uiState.num2
    val operation = uiState.operation
    val answers = uiState.answers
    val points = uiState.points
    val timer = uiState.timer
    val currentOperation = uiState.currentOperation
    val maxOperations = uiState.maxOperations
    val showResult = uiState.showResult
    val isCorrect = uiState.isCorrect
    LaunchedEffect(currentOperation, timer) {
        if (currentOperation >= maxOperations || timer <= 0) {
            navController.navigate(AppScreens.EndGameScreen.route + "/$gameId/$points")
            Log.d("MathScreen", "Fin del juego")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TimerCard(timer = timer)
            ScoreCard(points = points)
            OperationCard(
                currentOperation = currentOperation,
                maxOperations = maxOperations
            )
        }

        Text(
            text = "$num1 $operation $num2",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        answers.chunked(2).forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                row.forEach { answer ->
                    AnswerButton(answer = answer) {
                        addSubViewModel.checkAnswer(it)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(if (!showResult) 29.dp else 0.dp))
        if (showResult) {
            ResultText(isCorrect = isCorrect)
        }
    }
}

@Composable
fun TimerCard(timer: Int) {
    Card(
        modifier = Modifier
            .width(80.dp)
            .height(60.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$timer s",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ScoreCard(points: Int) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(60.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Puntos: $points",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun OperationCard(currentOperation: Int, maxOperations: Int) {
    Card(
        modifier = Modifier
            .width(80.dp)
            .height(60.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$currentOperation/$maxOperations",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AnswerButton(answer: Int, onClick: (Int) -> Unit) {
    Button(
        onClick = { onClick(answer) },
        modifier = Modifier
            .width(120.dp)
            .height(60.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF495D92))
    ) {
        Text(
            text = answer.toString(),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ResultText(isCorrect: Boolean) {
    Text(
        text = if (isCorrect) "¡Correcto!" else "¡Incorrecto!",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = if (isCorrect) Color.Green else Color.Red
    )
}
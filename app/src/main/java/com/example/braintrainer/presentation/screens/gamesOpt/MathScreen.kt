package com.example.braintrainer.presentation.screens.gamesOpt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.ViewModels.MathViewModel

@Composable
fun MathScreen(navController: NavHostController, mathViewModel: MathViewModel = viewModel()) {
    val uiState = mathViewModel.uiState.collectAsState()
    val num1 = uiState.value.num1
    val num2 = uiState.value.num2
    val answers = uiState.value.answers
    val points = uiState.value.points
    val timer = uiState.value.timer
    var showResult = uiState.value.showResult
    var isCorrect by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text("$timer s")
            Spacer(modifier = Modifier.width(16.dp))
            Text("Puntos: $points")
            Spacer(modifier = Modifier.width(16.dp))
            Text("1/25")

        }
        Text("$num1 + $num2", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(16.dp))
        for (i in 0..1) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (j in 0..1) {
                    val answerIndex = i * 2 + j
                    Button(onClick = {
                        isCorrect = mathViewModel.checkAnswer(answers[answerIndex])
                        showResult = true
                    }) { Text(answers[answerIndex].toString()) }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (showResult) {
            Text(
                if (isCorrect) "¡Correcto!" else "¡Incorrecto!",
                fontSize = 20.sp,
                color = if (isCorrect) Color.Green else Color.Red
            )
        }
    }
}
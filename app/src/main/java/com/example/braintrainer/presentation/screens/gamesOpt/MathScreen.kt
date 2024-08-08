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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.braintrainer.presentation.ViewModels.MathViewModel

@Composable
fun MathScreen(navController: NavHostController, mathViewModel: MathViewModel = viewModel()) {
    val uiState = mathViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text("30s")
            Spacer(modifier = Modifier.width(16.dp))
            Text("Puntos: 0")
        }
        Text(uiState.value.num1.toString() + " + " + uiState.value.num2.toString(), fontSize = 30.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { /*TODO*/ }) { Text(uiState.value.answers[0].toString()) }
            Button(onClick = { /*TODO*/ }) { Text(uiState.value.answers[1].toString()) }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { /*TODO*/ }) { Text(uiState.value.answers[2].toString()) }
            Button(onClick = { /*TODO*/ }) { Text(uiState.value.answers[3].toString()) }
        }
        Text("¡Correcto!", fontSize = 20.sp, color = Color.Green)
        Text("¡Incorrecto!", fontSize = 20.sp, color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun MathScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text("30s")
            Spacer(modifier = Modifier.width(16.dp))
            Text("Puntos: 0")
        }
        Text("10 + 5", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { /*TODO*/ }) { Text("15") }
            Button(onClick = { /*TODO*/ }) { Text("20") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { /*TODO*/ }) { Text("10") }
            Button(onClick = { /*TODO*/ }) { Text("5") }
        }
        Text("¡Correcto!", fontSize = 20.sp, color = Color.Green)
        Text("¡Incorrecto!", fontSize = 20.sp, color = Color.Red)
    }
}
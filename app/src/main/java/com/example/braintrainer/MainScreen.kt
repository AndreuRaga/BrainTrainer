package com.example.braintrainer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//Este archivo hará de pantalla de inicio en un futuro no muy lejano
@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "¡Bienvenido/a a Brain Trainer!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¿Ya tienes cuenta?")
        Button(onClick = { /* Acción al hacer clic */ }) {
            Text("Inicia sesión")
        }
        Text("¿No tienes cuenta?")
        Button(onClick = { /* Acción al hacer clic */ }) {
            Text("Regístrate con Google")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
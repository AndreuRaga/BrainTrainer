package com.example.braintrainer

import android.app.AlertDialog
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.braintrainer.navigation.AppScreens
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

//Este archivo hará de pantalla de autenticación en un futuro no muy lejano
@Composable
fun AuthScreen() {
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

//@Composable
//private fun googleSignIn() {
//    val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestEmail()
//        .build()
//    val googleClient: GoogleSignInClient = GogleSignIn.getClient(LocalContext.current, gso)
//}

@Composable
private fun ShowAlert() {
    val builder = AlertDialog.Builder(LocalContext.current)
    builder.setTitle("Error")
    builder.setMessage("Se ha procucido un error autenticando al usuario")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen()
}
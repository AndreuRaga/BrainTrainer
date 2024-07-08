package com.example.braintrainer

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavHostController
import com.example.braintrainer.navigation.AppScreens
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val WEB_CLIENT_ID = "794704105067-crclh9gp6m36frubpulagpgjjc3tjuub.apps.googleusercontent.com"
var auth: FirebaseAuth = Firebase.auth

@Composable
fun AuthScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)
    var showDialog by remember { mutableStateOf(false) }
    //Bug: No cambia de pantalla cuando se inicia sesión correctamente, solo al lanzar la aplicación con una sesión iniciada
    LaunchedEffect(auth.currentUser) {
        if (auth.currentUser != null) {
            navController.navigate(AppScreens.ConfigScreen.route)
        }
    }

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
        Button(onClick = {
            scope.launch { googleOneTap(context, credentialManager) }
        }) {
            Text("Inicia sesión")
        }
        Text("¿No tienes cuenta?")
        Button(onClick = { /* Acción al hacer clic */ }) {
            Text("Regístrate con Google")
        }
        if (showDialog) {
            ShowAlert { showDialog = false }
        }
    }
}

suspend fun googleOneTap(context: Context, credentialManager: CredentialManager) {

    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

    // Check if the user has already signed in
    val googleIdOptionAlreadySignedIn = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(WEB_CLIENT_ID)
        .setAutoSelectEnabled(false)
        .setNonce(String(CharArray(16) { allowedChars.random() }))
        .build()

    // Check if the user has not signed in
    val googleIdOptionNotSignedIn = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(WEB_CLIENT_ID)
        .setAutoSelectEnabled(false)
        .setNonce(String(CharArray(16) { allowedChars.random() }))
        .build()

    val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID)
        .setNonce(String(CharArray(16) { allowedChars.random() }))
        .build()

    val requestSignedIn = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOptionAlreadySignedIn)
        .build()

    val requestNotSignedIn = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOptionNotSignedIn)
        .build()

    val requestSignedInWithGoogle = GetCredentialRequest.Builder()
        .addCredentialOption(signInWithGoogleOption)
        .build()

    withContext(Dispatchers.IO) {
        try {
            val result = credentialManager.getCredential(context, requestSignedIn)
            Log.d("SignedIn", "The user already signed in!!!")
            handleSignIn(result)
        } catch (e: GetCredentialException) {
            Log.d("SignedIn", "The user has not signed in yet!!!")
            try {
                val result = credentialManager.getCredential(context, requestNotSignedIn)
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.d("SignedIn", "Something went very, very, very wrong!!!")
            }
        }
    }
}

fun handleSignIn(result: GetCredentialResponse) {
    val credential = result.credential
    when (credential) {
        // GoogleIdToken credential
        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    // Use googleIdTokenCredential and extract id to validate and
                    // authenticate on your server.
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    Log.d("Credentials", "User successfully signed in!")
                    firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e("Credentials", "Invalid Google ID Token response", e)
                }
            } else {
                Log.e("Credentials", "Unexpected type of credential")
            }
        }

        else -> {
            Log.e("Credentials", "Unexpected type of credential")
        }
    }
}

private fun firebaseAuthWithGoogle(idToken: String) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success
                Log.d("Success", "signInWithCredential:success")
            } else {
                // Sign in fails
                Log.w("Failure", "signInWithCredential:failure", task.exception)
            }
        }
}

@Composable
fun ShowAlert(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Error") },
        text = { Text("Se ha procucido un error autenticando al usuario.") },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Aceptar")
            }
        })
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    //AuthScreen()
}
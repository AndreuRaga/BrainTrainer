package com.example.braintrainer.data

import android.util.Log
import com.example.braintrainer.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : AuthRepository {
    override suspend fun signInWithCredential(idToken: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun deleteUser(): Boolean {
        //Log.d("AuthRepository", getCurrentUser()?.getIdToken(true).toString())
        return try {
            firebaseAuth.currentUser?.delete()?.await()
            true
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            false
        }
    }

    override suspend fun reauthUser(password: String?): Boolean {
        val email = getCurrentUser()?.email
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            Log.d("AuthRepository", "Email o contraseña inválidos")
            return false
        }
        val credential = GoogleAuthProvider.getCredential(email, password)
        return try {
            getCurrentUser()?.reauthenticate(credential)?.await()
            Log.d("AuthRepository", "User reauthenticated")
            true
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.d("AuthRepository", "User not reauthenticated")
            false
        }
    }

    override fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}
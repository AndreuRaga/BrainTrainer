package com.example.braintrainer.data.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {
    override suspend fun signInWithCredential(idToken: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential).await()
            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error during sign in", e)
            false
        }
    }

    override suspend fun reauthUser(password: String?): Boolean {
        val email = getCurrentUser()?.email
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            Log.d("AuthRepository", "Email o contraseña inválidos")
            return false
        }
        return try {
            val credential = GoogleAuthProvider.getCredential(email, password)
            getCurrentUser()?.reauthenticate(credential)?.await()
            Log.d("AuthRepository", "User reauthenticated")
            true
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AuthRepository", "User not reauthenticated", e)
            false
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun deleteUser(): Boolean {
        return try {
            firebaseAuth.currentUser?.delete()?.await()
            Log.d("AuthRepository", "User deleted")
            true
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            Log.e("AuthRepository", "User deletion failed", e)
            false
        }
    }

    override suspend fun reauthenticateWithGoogle(idToken: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.currentUser?.reauthenticateAndRetrieveData(credential)?.await()
            Log.d("AuthRepository", "User reauthenticated with Google")
            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Reauthentication failed", e)
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
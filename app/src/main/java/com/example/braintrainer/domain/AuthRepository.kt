package com.example.braintrainer.domain

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signInWithCredential(idToken: String): Boolean
    fun signOut()
    suspend fun deleteUser(): Boolean
    fun isUserSignedIn(): Boolean
    fun getCurrentUser(): FirebaseUser?
}
package com.example.braintrainer.domain

interface AuthRepository {
    suspend fun signInWithCredential(idToken: String): Boolean
    fun signOut()
    suspend fun deleteUser(): Boolean
    fun isUserSignedIn(): Boolean
}
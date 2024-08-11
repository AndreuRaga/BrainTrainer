package com.example.braintrainer.data.repositories

import com.example.braintrainer.data.models.User
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    suspend fun createUser(user: FirebaseUser)
    suspend fun getUserById(userId: String): User?
    fun updateUser(user: FirebaseUser)
    suspend fun deleteUser(userId: String)
}
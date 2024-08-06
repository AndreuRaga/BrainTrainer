package com.example.braintrainer.domain

import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    suspend fun createUser(user: FirebaseUser)
    fun getUserById(userId: String): FirebaseUser?
    fun updateUser(user: FirebaseUser)
    suspend fun deleteUser(userId: String)
}
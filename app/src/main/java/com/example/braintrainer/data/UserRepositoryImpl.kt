package com.example.braintrainer.data

import com.example.braintrainer.domain.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    private val db = Firebase.firestore

    override suspend fun createUser(user: FirebaseUser) {
        db.collection("users").document(user.uid).set(
            hashMapOf(
                "name" to user.displayName,
                "email" to user.email
            )).await()
    }

    override fun getUserById(userId: String): FirebaseUser? {
        return null
    }

    override fun updateUser(user: FirebaseUser) {

    }

    override suspend fun deleteUser(userId: String) {
        db.collection("users").document(userId).delete().await()
    }
}
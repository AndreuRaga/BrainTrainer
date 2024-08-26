package com.example.braintrainer.data.repositories

import android.util.Log
import com.example.braintrainer.data.models.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    private val db = Firebase.firestore

    override suspend fun createUser(user: FirebaseUser) {
        try {
            val userData = User(user.displayName, user.email)
            db.collection("users").document(user.uid).set(userData).await()
            Log.d("UserRepository", "User created successfully")
        } catch (e: Exception) {
            Log.w("UserRepository", "Error creating user", e)
        }
    }

    override suspend fun getUserById(userId: String): User? {
        return try {
            val document = db.collection("users").document(userId).get().await()
            if (document.exists()) document.toObject<User>() else null
        } catch (e: Exception) {
            Log.w("UserRepository", "Error getting user", e)
            null
        }
    }

    override fun updateUser(user: FirebaseUser) {

    }

    override suspend fun deleteUser(userId: String) {
        try {
            db.collection("users").document(userId).collection("scores").deleteDocuments()
            db.collection("users").document(userId).delete().await()
            Log.d("UserRepository", "User data deleted successfully from DB")
        } catch (e: Exception) {
            Log.w("UserRepository", "Error deleting user data from DB", e)
        }
    }

    private suspend fun CollectionReference.deleteDocuments() {
        get().await().documents.forEach { it.reference.delete().await() }
    }
}
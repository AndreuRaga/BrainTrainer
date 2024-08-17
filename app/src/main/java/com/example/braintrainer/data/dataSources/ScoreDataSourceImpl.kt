package com.example.braintrainer.data.dataSources

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ScoreDataSourceImpl @Inject constructor(private val db: FirebaseFirestore) : ScoreDataSource {
    override suspend fun saveScore(userId: String, gameId: String, points: Int): Result<Unit> {
        return try {
            val scoreData = hashMapOf(
                "points" to points
            )
            db.collection("users").document(userId).collection("scores").document(gameId).set(scoreData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
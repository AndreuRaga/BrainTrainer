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

    override suspend fun getScore(userId: String, gameId: String): Result<Int?> {
        return try {
            val document = db.collection("users").document(userId).collection("scores").document(gameId).get().await()
            if (document.exists()) {
                val pointsLong = document.get("points") as Long?
                val pointsInt = pointsLong?.toInt()
                Result.success(pointsInt)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
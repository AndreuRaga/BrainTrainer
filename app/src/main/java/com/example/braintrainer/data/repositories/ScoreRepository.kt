package com.example.braintrainer.data.repositories

interface ScoreRepository {
    suspend fun saveScore(userId: String, gameId: String, points: Int): Result<Unit>
}
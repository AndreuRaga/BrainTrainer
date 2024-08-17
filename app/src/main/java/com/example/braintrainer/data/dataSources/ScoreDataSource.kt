package com.example.braintrainer.data.dataSources

interface ScoreDataSource {
    suspend fun saveScore(userId: String, gameId: String, points: Int): Result<Unit>
}
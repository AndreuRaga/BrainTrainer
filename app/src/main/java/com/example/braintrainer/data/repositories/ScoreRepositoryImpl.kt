package com.example.braintrainer.data.repositories

import com.example.braintrainer.data.dataSources.ScoreDataSource
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor(private val scoreDataSource: ScoreDataSource) : ScoreRepository {
    override suspend fun saveScore(userId: String, gameId: String, points: Int): Result<Unit> {
        return scoreDataSource.saveScore(userId, gameId, points)
    }
}
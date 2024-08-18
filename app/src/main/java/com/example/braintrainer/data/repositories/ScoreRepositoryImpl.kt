package com.example.braintrainer.data.repositories

import com.example.braintrainer.data.dataSources.ScoreDataSource
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor(private val scoreDataSource: ScoreDataSource) : ScoreRepository {
    override suspend fun saveScore(userId: String, gameId: String, points: Int): Result<Unit> {
        return scoreDataSource.getScore(userId, gameId).mapCatching { existingScore ->
            if (existingScore == null || points > existingScore) {
                scoreDataSource.saveScore(userId, gameId, points)
            } else {
                Result.success(Unit)
            }
        }.getOrElse {
            Result.failure(it)
        }
    }
    override suspend fun getScore(userId: String, gameId: String): Result<Int?> {
        return scoreDataSource.getScore(userId, gameId)
    }
}
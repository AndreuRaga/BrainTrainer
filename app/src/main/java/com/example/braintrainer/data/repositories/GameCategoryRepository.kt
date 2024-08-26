package com.example.braintrainer.data.repositories

import com.example.braintrainer.data.models.Game
import com.example.braintrainer.data.models.GameCategory

interface GameCategoryRepository {
    suspend fun getCategoriesFromDB(): Result<List<GameCategory>>
    suspend fun getGameByIdFromDB(gameId: String): Result<Game?>
}
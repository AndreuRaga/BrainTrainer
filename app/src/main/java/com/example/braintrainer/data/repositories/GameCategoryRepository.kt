package com.example.braintrainer.data.repositories

import com.example.braintrainer.data.models.Game
import com.example.braintrainer.data.models.GameCategory

interface GameCategoryRepository {
    fun getGameCategories(): List<GameCategory>
    suspend fun getCategoriesFromDB(): Result<List<GameCategory>>
    suspend fun getGameByIdFromDB(gameId: String): Result<Game?>
    fun getGameById(gameId: String): Game?
}
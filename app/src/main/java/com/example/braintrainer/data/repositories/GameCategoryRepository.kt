package com.example.braintrainer.data.repositories

import com.example.braintrainer.data.models.Game
import com.example.braintrainer.data.models.GameCategory

interface GameCategoryRepository {
    fun getGameCategories(): List<GameCategory>
    fun getGameById(gameId: String): Game?
}
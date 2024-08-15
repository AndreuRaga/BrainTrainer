package com.example.braintrainer.data.repositories

import com.example.braintrainer.data.dataSources.GameCategoryDataSource
import com.example.braintrainer.data.models.Game
import com.example.braintrainer.data.models.GameCategory
import com.example.braintrainer.data.models.GameData
import javax.inject.Inject


class GameCategoryRepositoryImpl @Inject constructor(private val dataSource: GameCategoryDataSource) : GameCategoryRepository {
    override fun getGameCategories(): List<GameCategory> {
        return GameData.categories
    }

    override suspend fun getCategoriesFromDB(): Result<List<GameCategory>> {
        return dataSource.getCategories()
    }

    override fun getGameById(gameId: String): Game? {
        return GameData.categories.flatMap { it.games }.find { it.id == gameId }
    }
}
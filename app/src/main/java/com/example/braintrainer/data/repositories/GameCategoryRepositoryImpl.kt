package com.example.braintrainer.data.repositories

import com.example.braintrainer.data.models.GameCategory
import com.example.braintrainer.data.models.GameData
import javax.inject.Inject


class GameCategoryRepositoryImpl @Inject constructor() : GameCategoryRepository {
    override fun getGameCategories(): List<GameCategory> {
        return GameData.categories
    }
}
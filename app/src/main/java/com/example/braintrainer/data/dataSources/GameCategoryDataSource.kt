package com.example.braintrainer.data.dataSources

import com.example.braintrainer.data.models.GameCategory

interface GameCategoryDataSource {
    suspend fun getCategories(): Result<List<GameCategory>>
}
package com.example.braintrainer.data.dataSources

import com.example.braintrainer.data.models.Game
import com.example.braintrainer.data.models.GameCategory
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GameCategoryDataSourceImpl @Inject constructor(private val db: FirebaseFirestore) : GameCategoryDataSource {
    override suspend fun getCategories(): Result<List<GameCategory>> {
        return try {
            val categories = db.collection("categories").get()
                .await()
                .documents
                .map{ categoryDocument ->
                    val categoryId = categoryDocument.id
                    val categoryName = categoryDocument["name"] as String
                    val games = categoryDocument.reference.collection("games")
                        .get()
                        .await()
                        .documents
                        .map { gameDocument ->
                            val gameId = gameDocument.id
                            val gameName = gameDocument["name"] as String
                            val gameInstructions = gameDocument["instructions"] as String
                            Game(gameId, gameName, gameInstructions)
                        }
                    GameCategory(categoryId, categoryName, games)
                }
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
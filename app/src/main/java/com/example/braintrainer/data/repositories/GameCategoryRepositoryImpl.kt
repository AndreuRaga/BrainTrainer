package com.example.braintrainer.data.repositories

import android.util.Log
import com.example.braintrainer.data.models.Game
import com.example.braintrainer.data.models.GameCategory
import com.example.braintrainer.data.models.GameData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class GameCategoryRepositoryImpl @Inject constructor() : GameCategoryRepository {
    private val db = Firebase.firestore
    override fun getGameCategories(): List<GameCategory> {
        return GameData.categories
    }

    override suspend fun getCategoriesFromDB(): List<GameCategory> {
        return try {
            db.collection("categories")
                .get()
                .await()
                .documents
                .map { categoryDocument ->
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
        } catch (e: Exception) {
            Log.e("GameCategoryRepository", "Error al obtener categorías de la base de datos", e)
            emptyList()
        }
    }

    override fun getGameById(gameId: String): Game? {
        return GameData.categories.flatMap { it.games }.find { it.id == gameId }
    }
}
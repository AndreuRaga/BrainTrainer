package com.example.braintrainer.data.models

data class GameCategory(
    val id: String,
    val name: String,
    val games: List<Game>,
    val totalBestScore: Int? = null,
    val totalMaxScore: Int? = null,
    val progress: Float? = null
)
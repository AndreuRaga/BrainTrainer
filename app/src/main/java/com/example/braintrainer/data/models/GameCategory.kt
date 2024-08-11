package com.example.braintrainer.data.models

data class GameCategory(
    val id: String,
    val name: String,
    val games: List<Game>
)
package com.example.braintrainer.data.models

data class Game(
    val id: String,
    val name: String,
    val instructions: String,
    val maxScore: Int,
    val bestScore: Int? = null
)
package com.example.braintrainer.data.models

object GameData {
    val categories = listOf(
        GameCategory(
            id = "math",
            name = "Matemáticas",
            games = listOf(
                Game(
                    id = "add_sub",
                    name = "Suma y resta",
                    instructions = "Sumar dos números"
                )
            )
        ),
        GameCategory(
            id = "memory",
            name = "Memoria",
            games = listOf(
                Game(
                    id = "cards",
                    name = "Cartas",
                    instructions = "Reordena las cartas"
                )
            )
        )
    )
}
package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.braintrainer.data.models.Game
import com.example.braintrainer.data.repositories.GameCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val gameCategoryRepository: GameCategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val gameId: String = checkNotNull(savedStateHandle["gameId"])
    fun getGame(): Game? {
        return gameCategoryRepository.getGameById(gameId)
    }
}
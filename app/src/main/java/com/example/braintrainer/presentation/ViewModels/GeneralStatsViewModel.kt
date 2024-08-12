package com.example.braintrainer.presentation.ViewModels

import androidx.lifecycle.ViewModel
import com.example.braintrainer.data.models.GameCategory
import com.example.braintrainer.data.repositories.GameCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GeneralStatsViewModel @Inject constructor(
    private val gameCategoryRepository: GameCategoryRepository
) : ViewModel() {
    fun getGameCategories(): List<GameCategory> {
        return gameCategoryRepository.getGameCategories()
    }
}
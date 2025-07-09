package com.example.technicaltest.domain.usecase

import com.example.technicaltest.domain.model.CharacterModel
import com.example.technicaltest.domain.repository.CharacterRepository
import javax.inject.Inject

class SaveFavoriteUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: CharacterModel) = repository.saveFavorite(character)
}
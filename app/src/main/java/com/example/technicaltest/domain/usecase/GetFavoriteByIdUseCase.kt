package com.example.technicaltest.domain.usecase

import com.example.technicaltest.domain.repository.CharacterRepository
import javax.inject.Inject

class GetFavoriteByIdUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int) = repository.getFavoriteById(id)
}
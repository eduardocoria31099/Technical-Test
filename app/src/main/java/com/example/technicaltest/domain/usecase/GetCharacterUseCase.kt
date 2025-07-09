package com.example.technicaltest.domain.usecase

import com.example.technicaltest.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(
        name: String? = null,
        status: String? = null,
        species: String? = null
    ) = characterRepository.getCharacters(name, status, species)
}
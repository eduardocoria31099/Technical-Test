package com.example.technicaltest.domain.repository

import androidx.paging.PagingData
import com.example.technicaltest.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null
    ): Flow<PagingData<CharacterModel>>

    suspend fun getFavoriteById(id: Int): Flow<CharacterModel?>

    suspend fun saveFavorite(character: CharacterModel)

    suspend fun deleteFavorite(id: Int)

    suspend fun getAllCharacters(): Flow<List<CharacterModel>>
}
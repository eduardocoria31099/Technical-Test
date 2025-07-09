package com.example.technicaltest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.technicaltest.data.local.CharacterDao
import com.example.technicaltest.data.network.ApiService
import com.example.technicaltest.data.paging.CharacterPagingSource
import com.example.technicaltest.domain.mapper.toDomain
import com.example.technicaltest.domain.mapper.toEntity
import com.example.technicaltest.domain.model.CharacterModel
import com.example.technicaltest.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private var service: ApiService,
    private var local: CharacterDao
) : CharacterRepository {
    override fun getCharacters(
        name: String?,
        status: String?,
        species: String?
    ): Flow<PagingData<CharacterModel>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            CharacterPagingSource(
                apiService = service,
                dao = local,
                name = name,
                status = status,
                species = species
            )
        }
    ).flow

    override suspend fun getFavoriteById(id: Int) = flow {
        emit(local.getFavoriteById(id).let { it?.toDomain() })
    }.flowOn(Dispatchers.IO)

    override suspend fun saveFavorite(character: CharacterModel) {
        local.insertFavorite(character.toEntity())
    }

    override suspend fun deleteFavorite(id: Int) {
        local.deleteFavoriteById(id)
    }

    override suspend fun getAllCharacters() = flow {
        emit(local.getFavoriteCharacters().map { it.toDomain() })
    }.flowOn(Dispatchers.IO)

}
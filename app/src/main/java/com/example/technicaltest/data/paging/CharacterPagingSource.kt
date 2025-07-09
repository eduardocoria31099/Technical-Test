package com.example.technicaltest.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.technicaltest.data.local.CharacterDao
import com.example.technicaltest.data.network.ApiService
import com.example.technicaltest.domain.mapper.toDomain
import com.example.technicaltest.domain.mapper.toEntity
import com.example.technicaltest.domain.model.CharacterModel

class CharacterPagingSource(
    private val apiService: ApiService,
    private val dao: CharacterDao,
    private val name: String? = null,
    private val status: String? = null,
    private val species: String? = null
) : PagingSource<Int, CharacterModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        val currentPage = params.key ?: 1

        return try {
            val response = apiService.getCharacters(
                page = currentPage,
                name = name,
                status = status,
                species = species
            )
            val charactersResponse = response.body()

            charactersResponse?.results?.map { it.toEntity() }?.let { dao.insertAll(it) }

            val data = charactersResponse?.results?.map { it.toDomain() } ?: emptyList()
            val nextPage = if (charactersResponse?.info?.next != null) currentPage + 1 else null

            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}




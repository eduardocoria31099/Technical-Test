package com.example.technicaltest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.technicaltest.domain.model.CharacterModel
import com.example.technicaltest.domain.usecase.DeleteFavoriteUseCase
import com.example.technicaltest.domain.usecase.GetCharacterUseCase
import com.example.technicaltest.domain.usecase.GetFavoriteByIdUseCase
import com.example.technicaltest.domain.usecase.SaveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getFavoriteByIdUseCase: GetFavoriteByIdUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {


    private val _filters = MutableStateFlow(Triple<String?, String?, String?>(null, null, null))
    private val filters = _filters.asStateFlow()

    private var _isFavorite = MutableSharedFlow<Pair<Boolean, CharacterModel>>()
    val isFavorite: SharedFlow<Pair<Boolean, CharacterModel>> = _isFavorite

    val characters: Flow<PagingData<CharacterModel>> = filters
        .flatMapLatest { (name, status, species) ->
            getCharacterUseCase(name, status, species)
        }
        .cachedIn(viewModelScope)

    fun applyFilters(name: String?, status: String?, species: String?) {
        _filters.value = Triple(name, status, species)
    }

    fun isCharacterFavorite(character: CharacterModel) = viewModelScope.launch {
        getFavoriteByIdUseCase(character.id).collect {
            _isFavorite.emit(Pair(it != null && it.isFavorite, character))
        }
    }


    fun onFavoriteToggled(character: CharacterModel, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                saveFavoriteUseCase(character.copy(isFavorite = true))
            } else {
                deleteFavoriteUseCase(character.id)
            }
        }
    }
}
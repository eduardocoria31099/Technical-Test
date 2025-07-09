package com.example.technicaltest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technicaltest.domain.model.CharacterModel
import com.example.technicaltest.domain.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    private val _favoriteCharacters = MutableStateFlow<List<CharacterModel>>(emptyList())
    val favoriteCharacters: StateFlow<List<CharacterModel>> = _favoriteCharacters

    fun getCharacters() = viewModelScope.launch {
        getAllCharactersUseCase.invoke().collect {
            _favoriteCharacters.emit(it)
        }
    }

}
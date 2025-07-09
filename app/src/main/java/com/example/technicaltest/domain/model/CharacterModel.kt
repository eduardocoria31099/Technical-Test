package com.example.technicaltest.domain.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val originName: String,
    val locationName: String,
    val isFavorite: Boolean = false
)
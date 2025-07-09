package com.example.technicaltest.domain.mapper

import com.example.technicaltest.data.local.CharacterEntity
import com.example.technicaltest.data.network.Character
import com.example.technicaltest.domain.model.CharacterModel

fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image,
    )
}

fun Character.toDomain(): CharacterModel {
    return CharacterModel(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image,
        originName = origin.name,
        locationName = location.name
    )
}

fun CharacterModel.toEntity(): CharacterEntity = CharacterEntity(
    id = id,
    name = name,
    image = image,
    gender = gender,
    species = species,
    status = status,
    isFavorite = true
)

fun CharacterEntity.toDomain(): CharacterModel = CharacterModel(
    id = id,
    name = name,
    image = image,
    gender = gender,
    species = species,
    status = status,
    originName = "",
    locationName = "",
    isFavorite = isFavorite
)

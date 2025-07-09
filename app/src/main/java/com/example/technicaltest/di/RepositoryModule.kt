package com.example.technicaltest.di

import com.example.technicaltest.data.repository.CharacterRepositoryImpl
import com.example.technicaltest.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository

}
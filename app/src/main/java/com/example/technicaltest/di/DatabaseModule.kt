package com.example.technicaltest.di

import android.content.Context
import androidx.room.Room
import com.example.technicaltest.data.local.AppDatabase
import com.example.technicaltest.data.local.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "character_db"
        ).build()
    }

    @Provides
    fun provideCharacterDao(db: AppDatabase): CharacterDao = db.characterDao()
}

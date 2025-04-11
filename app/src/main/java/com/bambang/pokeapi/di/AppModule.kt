package com.bambang.pokeapi.di

import android.content.Context
import com.bambang.pokeapi.data.local.DatabaseHelper
import com.bambang.pokeapi.data.repository.UserRepositoryImpl
import com.bambang.pokeapi.domain.repository.UserRepository
import com.bambang.pokeapi.data.local.SessionManager
import com.bambang.pokeapi.data.remote.api.PokeApiService
import com.bambang.pokeapi.data.repository.PokemonRepositoryImpl
import com.bambang.pokeapi.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabaseHelper(@ApplicationContext context: Context): DatabaseHelper {
        return DatabaseHelper(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(dbHelper: DatabaseHelper): UserRepository {
        return UserRepositoryImpl(dbHelper)
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    @Provides
    fun providePokemonRepository(
        api: PokeApiService,
        dbHelper: DatabaseHelper
    ): PokemonRepository {
        return PokemonRepositoryImpl(api, dbHelper)
    }
}
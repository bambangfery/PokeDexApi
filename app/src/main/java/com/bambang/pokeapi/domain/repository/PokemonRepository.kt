package com.bambang.pokeapi.domain.repository

import com.bambang.pokeapi.domain.model.Pokemon

interface PokemonRepository {
    suspend fun fetchPokemonPage(limit: Int, offset: Int): Result<Unit>
    suspend fun getLocalPokemonPage(limit: Int, offset: Int): List<Pokemon>
    suspend fun getLocalPokemonCount(): Int
}
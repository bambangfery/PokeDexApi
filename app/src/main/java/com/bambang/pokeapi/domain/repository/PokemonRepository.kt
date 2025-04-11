package com.bambang.pokeapi.domain.repository

import com.bambang.pokeapi.domain.model.Pokemon

interface PokemonRepository {
    suspend fun fetchPokemon(): List<Pokemon>
    fun getLocalPokemon(): List<Pokemon>
}
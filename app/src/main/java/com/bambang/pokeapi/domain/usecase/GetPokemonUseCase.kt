package com.bambang.pokeapi.domain.usecase

import com.bambang.pokeapi.domain.model.Pokemon
import com.bambang.pokeapi.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend fun fetchAndSave(): List<Pokemon> = repository.fetchPokemon()
    fun getLocal(): List<Pokemon> = repository.getLocalPokemon()
}
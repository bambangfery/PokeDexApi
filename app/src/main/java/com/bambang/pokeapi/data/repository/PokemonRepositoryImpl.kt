package com.bambang.pokeapi.data.repository

import com.bambang.pokeapi.data.local.DatabaseHelper
import com.bambang.pokeapi.data.mapper.toPokemon
import com.bambang.pokeapi.data.remote.api.PokeApiService
import com.bambang.pokeapi.domain.model.Pokemon
import com.bambang.pokeapi.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApiService,
    private val db: DatabaseHelper
) : PokemonRepository {

    override suspend fun fetchPokemon(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPokemonList()
                if (response.isSuccessful) {
                    val pokemons = response.body()?.results?.map { it.toPokemon() } ?: emptyList()
                    db.clearPokemons()
                    db.insertPokemonList(pokemons)
                    pokemons
                } else {
                    db.getAllPokemon()
                }
            } catch (e: Exception) {
                db.getAllPokemon()
            }
        }
    }

    override fun getLocalPokemon(): List<Pokemon> = db.getAllPokemon()
}
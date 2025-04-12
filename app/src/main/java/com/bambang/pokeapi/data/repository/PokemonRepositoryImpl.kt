package com.bambang.pokeapi.data.repository

import com.bambang.pokeapi.data.local.DatabaseHelper
import com.bambang.pokeapi.data.mapper.toPokemon
import com.bambang.pokeapi.data.remote.api.PokeApiService
import com.bambang.pokeapi.domain.model.Pokemon
import com.bambang.pokeapi.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApiService,
    private val db: DatabaseHelper
) : PokemonRepository {

    override suspend fun fetchPokemonPage(limit: Int, offset: Int): Result<Unit> {
        return try {
            val response = api.getPokemonList(limit, offset)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                val pokemons = body.results.map { it.toPokemon() }
                db.insertPokemonList(pokemons)
                Result.success(Unit)
            } else {
                Result.failure(Exception("error ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLocalPokemonPage(limit: Int, offset: Int): List<Pokemon> {
        return db.getPokemonList(limit, offset).map {
            Pokemon(it.name, it.url)
        }
    }

    override suspend fun getLocalPokemonCount(): Int {
        return db.getPokemonCount()
    }
}
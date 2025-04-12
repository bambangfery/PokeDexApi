package com.bambang.pokeapi.data.repository

import com.bambang.pokeapi.data.local.DatabaseHelper
import com.bambang.pokeapi.data.mapper.toPokemon
import com.bambang.pokeapi.data.remote.api.PokeApiService
import com.bambang.pokeapi.data.remote.dto.PokemonDetailResponse
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

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetailResponse> {
        return try {
            val response = api.getPokemonDetail(name)
            if (response.isSuccessful) {
                response.body()?.let {
                    db.insertPokemonDetail(it)
                    Result.success(it)
                } ?: Result.failure(Exception("Empty Body"))
            } else {
                getLocalPokemonDetailOrError(name, "API error ${response.code()}")
            }
        } catch (e: Exception) {
            getLocalPokemonDetailOrError(name, e.message ?: "Unknown error")
        }
    }

    private fun getLocalPokemonDetailOrError(name: String, errorMessage: String): Result<PokemonDetailResponse> {
        val local = db.getPokemonDetail(name)
        return if (local != null) {
            Result.success(local)
        } else {
            Result.failure(Exception("Failed from API and no local cache: $errorMessage"))
        }
    }
}
package com.bambang.pokeapi.data.remote.api

import com.bambang.pokeapi.data.remote.dto.PokemonDetailResponse
import com.bambang.pokeapi.data.remote.dto.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): Response<PokemonDetailResponse>
}
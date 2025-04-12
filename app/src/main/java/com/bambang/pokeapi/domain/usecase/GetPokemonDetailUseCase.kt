package com.bambang.pokeapi.domain.usecase

import com.bambang.pokeapi.data.remote.dto.PokemonDetailResponse
import com.bambang.pokeapi.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend fun execute(name: String): Result<PokemonDetailResponse> {
        return repository.getPokemonDetail(name)
    }
}
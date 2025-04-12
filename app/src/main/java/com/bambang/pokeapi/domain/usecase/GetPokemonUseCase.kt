package com.bambang.pokeapi.domain.usecase

import com.bambang.pokeapi.domain.model.Pokemon
import com.bambang.pokeapi.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend fun execute(page: Int, pageSize: Int): Result<List<Pokemon>> {
        val offset = page * pageSize
        val currentCount = repository.getLocalPokemonCount()

        if (offset >= currentCount) {
            repository.fetchPokemonPage(pageSize, offset)
        }

        val list = repository.getLocalPokemonPage(pageSize, offset)
        return Result.success(list)
    }
}
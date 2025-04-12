package com.bambang.pokeapi.data.mapper

import com.bambang.pokeapi.data.remote.dto.PokemonResult
import com.bambang.pokeapi.domain.model.Pokemon


fun PokemonResult.toPokemon(): Pokemon {
    val id = url.trimEnd('/').split("/").last()
    val imageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    return Pokemon(name = name, url = imageUrl)
}

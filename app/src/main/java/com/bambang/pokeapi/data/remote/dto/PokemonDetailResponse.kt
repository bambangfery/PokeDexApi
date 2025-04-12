package com.bambang.pokeapi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val name: String,
    val abilities: List<AbilitySlot>,
    val sprites: Sprites
)

data class AbilitySlot(
    val ability: Ability
)

data class Ability(
    val name: String
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String
)
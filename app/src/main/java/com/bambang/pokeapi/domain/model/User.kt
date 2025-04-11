package com.bambang.pokeapi.domain.model

data class User(
    val id: Int = 0,
    val username: String,
    val name: String,
    val password: String
)
package com.bambang.pokeapi.domain.repository

import com.bambang.pokeapi.domain.model.User

interface UserRepository {
    suspend fun register(user: User): Result<String>
    suspend fun login(username: String, password: String): Result<User>
}
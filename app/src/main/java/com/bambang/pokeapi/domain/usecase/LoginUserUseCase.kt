package com.bambang.pokeapi.domain.usecase

import com.bambang.pokeapi.domain.model.User
import com.bambang.pokeapi.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute(username: String, password: String): Result<User> {
        return repository.login(username, password)
    }
}
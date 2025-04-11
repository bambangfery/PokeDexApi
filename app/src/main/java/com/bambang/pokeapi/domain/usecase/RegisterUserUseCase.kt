package com.bambang.pokeapi.domain.usecase

import com.bambang.pokeapi.domain.model.User
import com.bambang.pokeapi.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<String> {
        return repository.register(user)
    }
}
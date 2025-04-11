package com.bambang.pokeapi.data.repository

import com.bambang.pokeapi.data.local.DatabaseHelper
import com.bambang.pokeapi.domain.model.User
import com.bambang.pokeapi.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dbHelper: DatabaseHelper
) : UserRepository {
    override suspend fun register(user: User): Result<String> {
        return withContext(Dispatchers.IO) {
            val success = dbHelper.insertUser(user)
            if (success) {
                Result.success("Registrasi berhasil")
            } else {
                Result.failure(Exception("Username sudah digunakan"))
            }
        }
    }

    override suspend fun login(username: String, password: String): Result<User> {
        return withContext(Dispatchers.IO) {
            val user = dbHelper.getUser(username, password)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Username atau password salah"))
            }
        }
    }
}
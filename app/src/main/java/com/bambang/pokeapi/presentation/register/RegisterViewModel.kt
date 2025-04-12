package com.bambang.pokeapi.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambang.pokeapi.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import com.bambang.pokeapi.domain.usecase.RegisterUserUseCase

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    val registerResult = MutableLiveData<Result<String>>()

    fun register(username: String, name: String, password: String) {
        viewModelScope.launch {
            val user = User(username = username, name = name, password = password)
            val result = registerUserUseCase.execute(user)
            registerResult.postValue(result)
        }
    }
}
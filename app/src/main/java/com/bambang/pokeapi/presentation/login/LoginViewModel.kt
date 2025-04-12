package com.bambang.pokeapi.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import com.bambang.pokeapi.domain.model.User
import com.bambang.pokeapi.domain.usecase.LoginUserUseCase

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    val loginResult = MutableLiveData<Result<User>>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = loginUserUseCase.execute(username, password)
            loginResult.postValue(result)
        }
    }
}
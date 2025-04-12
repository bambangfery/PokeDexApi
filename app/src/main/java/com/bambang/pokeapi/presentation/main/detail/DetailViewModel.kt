package com.bambang.pokeapi.presentation.main.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambang.pokeapi.data.remote.dto.PokemonDetailResponse
import com.bambang.pokeapi.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _pokemonDetail = MutableStateFlow<PokemonDetailResponse?>(null)
    val pokemonDetail: StateFlow<PokemonDetailResponse?> = _pokemonDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadPokemonDetail(name: String) {
        _isLoading.value = true
        viewModelScope.launch {
            useCase.execute(name).onSuccess {
                _pokemonDetail.value = it
            }.onFailure {
                _errorMessage.value = it.message ?: "Terjadi kesalahan"
            }
            _isLoading.value = false
        }
    }
}
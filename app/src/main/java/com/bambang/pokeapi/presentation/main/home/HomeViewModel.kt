package com.bambang.pokeapi.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambang.pokeapi.domain.model.Pokemon
import com.bambang.pokeapi.domain.usecase.GetPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetPokemonUseCase
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList

    private var currentPage = 0
    private val pageSize = 10
    private var isLoading = false

    fun loadNextPage() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            val result = useCase.execute(currentPage, pageSize)
            result.onSuccess { newItems ->
                _pokemonList.update { old ->
                    val updated = old + newItems
                    updated
                }

                if (newItems.isNotEmpty()) {
                    currentPage++
                }
            }
            isLoading = false
        }
    }
}
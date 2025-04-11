package com.bambang.pokeapi.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bambang.pokeapi.domain.model.Pokemon
import com.bambang.pokeapi.domain.usecase.GetPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetPokemonUseCase
) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    fun loadPokemon() {
        viewModelScope.launch {
            val list = useCase.fetchAndSave()
            _pokemonList.postValue(list)
        }
    }

    fun loadFromDb() {
        _pokemonList.value = useCase.getLocal()
    }
}
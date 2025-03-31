package com.bcit.pokemon_api.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.bcit.pokemon_api.ui.data.PokemonRepository
import com.bcit.pokemon_api.ui.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PokemonRepository(application)
    val history: Flow<List<Pokemon>> = repository.getAllHistory()

    fun insertPokemon(pokemon: Pokemon) {
        viewModelScope.launch { repository.insertPokemon(pokemon) }
    }
}
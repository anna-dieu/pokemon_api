package com.bcit.pokemon_api.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bcit.pokemon_api.ui.data.PokemonRepository
import com.bcit.pokemon_api.ui.data.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PokemonRepository(application.applicationContext)
    
    // Map PokemonHistory to Pokemon with proper default parameters
    val history: Flow<List<Pokemon>> = repository.getAllHistory().map { historyList ->
        historyList.map { history ->
            Pokemon(
                id = history.pokemonId,
                name = history.name,
                imageUrl = history.imageUrl,
                // Provide default values for other required parameters
                height = 0,
                weight = 0,
                types = ""
            )
        }
    }

    fun savePokemon(pokemon: Pokemon) {
        viewModelScope.launch { 
            repository.savePokemonToHistory(pokemon) 
        }
    }
}
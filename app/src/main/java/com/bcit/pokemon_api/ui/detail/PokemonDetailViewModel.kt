package com.bcit.pokemon_api.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bcit.pokemon_api.ui.data.PokemonRepository
import com.bcit.pokemon_api.ui.data.model.Pokemon
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    application: Application, 
    private val pokemonId: Int
) : AndroidViewModel(application) {
    
    private val repository = PokemonRepository(application)
    
    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    init {
        loadPokemon()
    }
    
    private fun loadPokemon() {
        _loading.value = true
        _error.value = ""
        
        viewModelScope.launch {
            try {
                // For now, use the random Pokemon method
                // In a full implementation, you'd call a method like getPokemonById
                val result = repository.getRandomPokemon()
                
                _loading.value = false
                
                result.fold(
                    onSuccess = { pokemon ->
                        _pokemon.value = pokemon
                        // Save to history
                        repository.savePokemonToHistory(pokemon)
                    },
                    onFailure = { error -> 
                        _error.value = "Error loading Pokemon: ${error.message}"
                    }
                )
            } catch (e: Exception) {
                _loading.value = false
                _error.value = "Error loading Pokemon: ${e.message}"
            }
        }
    }
}

// Factory to create the ViewModel with the pokemonId argument
class PokemonDetailViewModelFactory(
    private val application: Application, 
    private val pokemonId: Int
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonDetailViewModel(application, pokemonId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
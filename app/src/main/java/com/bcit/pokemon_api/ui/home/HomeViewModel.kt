package com.bcit.pokemon_api.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bcit.pokemon_api.ui.data.PokemonRepository
import com.bcit.pokemon_api.ui.data.model.Pokemon
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PokemonRepository(application.applicationContext)
    
    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    fun loadRandomPokemon() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            
            try {
                // Make sure this method exists in your repository
                val result = repository.getRandomPokemon()
                
                if (result.isSuccess) {
                    result.getOrNull()?.let { pokemon ->
                        _pokemon.value = pokemon
                        repository.savePokemonToHistory(pokemon)
                    }
                } else {
                    _error.value = result.exceptionOrNull()?.message ?: "Unknown error"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            } finally {
                _loading.value = false
            }
        }
    }
}


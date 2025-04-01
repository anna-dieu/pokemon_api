package com.bcit.pokemon_api.ui.dashboard

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

class TypeDetailViewModel(application: Application, private val typeName: String) : AndroidViewModel(application) {
    
    private val repository = PokemonRepository(application)
    
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    init {
        loadPokemonByType()
    }
    
    private fun loadPokemonByType() {
        _loading.value = true
        _error.value = ""
        
        viewModelScope.launch {
            val result = repository.getPokemonByType(typeName)
            
            _loading.value = false
            
            result.fold(
                onSuccess = { pokemonList ->
                    _pokemonList.value = pokemonList
                },
                onFailure = { error -> 
                    _error.value = "Error loading Pokemon: ${error.message}"
                }
            )
        }
    }
}

// Factory to create the ViewModel with arguments
class TypeDetailViewModelFactory(
    private val application: Application, 
    private val typeName: String
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TypeDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TypeDetailViewModel(application, typeName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
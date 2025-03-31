package com.bcit.pokemon_api.ui.dashboard

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bcit.pokemon_api.ui.data.PokemonRepository
import com.bcit.pokemon_api.ui.data.model.PokemonType
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PokemonRepository(application)

    private val _pokemonTypes = MutableLiveData<List<PokemonType>>()
    val pokemonTypes: LiveData<List<PokemonType>> = _pokemonTypes

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        loadPokemonTypes()
    }

    private fun loadPokemonTypes() {
        val types = listOf(
            PokemonType("normal", "Normal", Color.parseColor("#A8A878")),
            PokemonType("fire", "Fire", Color.parseColor("#F08030")),
            PokemonType("water", "Water", Color.parseColor("#6890F0")),
            PokemonType("grass", "Grass", Color.parseColor("#78C850")),
            PokemonType("electric", "Electric", Color.parseColor("#F8D030")),
            PokemonType("ice", "Ice", Color.parseColor("#98D8D8")),
            PokemonType("fighting", "Fighting", Color.parseColor("#C03028")),
            PokemonType("poison", "Poison", Color.parseColor("#A040A0")),
            PokemonType("ground", "Ground", Color.parseColor("#E0C068")),
            PokemonType("flying", "Flying", Color.parseColor("#A890F0")),
            PokemonType("psychic", "Psychic", Color.parseColor("#F85888")),
            PokemonType("bug", "Bug", Color.parseColor("#A8B820")),
            PokemonType("rock", "Rock", Color.parseColor("#B8A038")),
            PokemonType("ghost", "Ghost", Color.parseColor("#705898")),
            PokemonType("dragon", "Dragon", Color.parseColor("#7038F8")),
            PokemonType("dark", "Dark", Color.parseColor("#705848")),
            PokemonType("steel", "Steel", Color.parseColor("#B8B8D0")),
            PokemonType("fairy", "Fairy", Color.parseColor("#EE99AC"))
        )
        _pokemonTypes.value = types
    }

    fun loadPokemonByType(typeName: String) {
        _loading.value = true
        _error.value = ""
        
        viewModelScope.launch {
            val result = repository.getPokemonByType(typeName)
            
            _loading.value = false
            
            result.fold(
                onSuccess = { /* Handle the Pokemon list here if needed */ },
                onFailure = { error -> _error.value = "Error loading Pokemon: ${error.message}" }
            )
        }
    }
}
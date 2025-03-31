package com.bcit.pokemon_api.ui.data

import android.app.Application
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.bcit.pokemon_api.ui.data.model.Pokemon
import com.bcit.pokemon_api.ui.data.network.PokemonApiService

class PokemonRepository(application: Application) {
    private val db = PokemonDatabase.getDatabase(application)
    private val dao = db.pokemonDao()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(PokemonApiService::class.java)

    fun getAllHistory(): Flow<List<Pokemon>> = dao.getAllHistory()

    suspend fun insertPokemon(pokemon: Pokemon) {
        dao.insert(pokemon)
    }
}

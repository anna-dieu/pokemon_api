package com.bcit.pokemon_api.ui.data

import android.content.Context
import com.bcit.pokemon_api.ui.data.db.PokemonDatabase
import com.bcit.pokemon_api.ui.data.db.PokemonHistory
import com.bcit.pokemon_api.ui.data.model.Pokemon
import com.bcit.pokemon_api.ui.data.model.PokemonApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class PokemonRepository(context: Context) {
    private val db = PokemonDatabase.getInstance(context)
    private val historyDao = db.pokemonHistoryDao
    
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val api = retrofit.create(PokemonApiService::class.java)

    fun getAllHistory(): Flow<List<PokemonHistory>> {
        return historyDao.getAllHistory()
    }

    suspend fun savePokemonToHistory(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            val history = PokemonHistory(
                pokemonId = pokemon.id,
                name = pokemon.name,
                imageUrl = pokemon.imageUrl,
                viewedAt = System.currentTimeMillis()
            )
            historyDao.insert(history)
        }
    }

    suspend fun getRandomPokemon(): Result<Pokemon> {
        return withContext(Dispatchers.IO) {
            try {
                val randomId = Random.nextInt(1, 898)
                val response = api.getPokemonById(randomId)
                
                if (response.isSuccessful) {
                    val pokemonResponse = response.body()
                    if (pokemonResponse != null) {
                        val pokemon = Pokemon(
                            id = pokemonResponse.id,
                            name = pokemonResponse.name,
                            height = pokemonResponse.height,
                            weight = pokemonResponse.weight,
                            imageUrl = pokemonResponse.sprites.front_default,
                            types = pokemonResponse.types.joinToString(", ") { it.type.name }
                        )
                        Result.success(pokemon)
                    } else {
                        Result.failure(Exception("Pokemon response body was null"))
                    }
                } else {
                    Result.failure(Exception("API request failed with code: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getPokemonByType(typeName: String): Result<List<Pokemon>> {
        return withContext(Dispatchers.IO) {
            try {
                // For now, just return an empty list
                Result.success(emptyList())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
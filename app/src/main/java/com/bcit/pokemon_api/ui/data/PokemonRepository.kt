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
                val response = api.getPokemonByType(typeName)
                
                if (response.isSuccessful) {
                    val typeResponse = response.body()
                    if (typeResponse != null) {
                        // Get the first 20 Pokemon (for performance reasons)
                        val pokemonList = typeResponse.pokemon.take(20)
                        
                        // For each Pokemon in the list, extract its ID from the URL
                        val detailedPokemonList = pokemonList.mapNotNull { pokemonListItem ->
                            val url = pokemonListItem.pokemon.url
                            val id = url.split("/").dropLast(1).last().toIntOrNull()
                            
                            if (id != null) {
                                try {
                                    val detailResponse = api.getPokemonById(id)
                                    if (detailResponse.isSuccessful) {
                                        val pokemonDetail = detailResponse.body()
                                        if (pokemonDetail != null) {
                                            Pokemon(
                                                id = pokemonDetail.id,
                                                name = pokemonDetail.name,
                                                height = pokemonDetail.height,
                                                weight = pokemonDetail.weight,
                                                imageUrl = pokemonDetail.sprites.front_default,
                                                types = pokemonDetail.types.joinToString(", ") { it.type.name }
                                            )
                                        } else null
                                    } else null
                                } catch (e: Exception) {
                                    null
                                }
                            } else null
                        }
                        
                        Result.success(detailedPokemonList)
                    } else {
                        Result.failure(Exception("Type response body was null"))
                    }
                } else {
                    Result.failure(Exception("API request failed with code: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getPokemonById(id: Int): Result<Pokemon> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPokemonById(id)
                
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
}
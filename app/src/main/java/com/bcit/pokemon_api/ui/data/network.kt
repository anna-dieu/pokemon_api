// Retrofit API Service
package com.bcit.pokemon_api.ui.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

data class PokemonApiResponse(val id: Int, val name: String, val sprites: Sprites, val types: List<TypeSlot>)
data class Sprites(val front_default: String)
data class TypeSlot(val type: Type)
data class Type(val name: String)

interface PokemonApiService {
    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<PokemonApiResponse>
}

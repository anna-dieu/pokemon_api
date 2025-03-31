package com.bcit.pokemon_api.ui.data

import com.bcit.pokemon_api.ui.data.model.PokemonApiResponse
import com.bcit.pokemon_api.ui.data.model.TypeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): Response<PokemonApiResponse>
    
    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Response<PokemonApiResponse>
    
    @GET("type/{name}")
    suspend fun getPokemonByType(@Path("name") typeName: String): Response<TypeResponse>
}
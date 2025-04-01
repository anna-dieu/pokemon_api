package com.bcit.pokemon_api.ui.data.model

data class TypeApiResponse(
    val id: Int,
    val name: String,
    val pokemon: List<PokemonListItem>
)

data class PokemonListItem(
    val pokemon: NamedApiResource,
    val slot: Int
)

data class NamedApiResource(
    val name: String,
    val url: String
)
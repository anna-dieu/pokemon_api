package com.bcit.pokemon_api.ui.data.model

data class TypeResponse(
    val pokemon: List<PokemonTypeEntry>
)

data class PokemonTypeEntry(
    val pokemon: NamedApiResource,
    val slot: Int
)

data class NamedApiResource(
    val name: String,
    val url: String
)
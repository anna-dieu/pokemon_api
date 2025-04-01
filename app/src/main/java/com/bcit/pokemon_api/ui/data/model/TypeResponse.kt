package com.bcit.pokemon_api.ui.data.model

data class TypeResponse(
    val pokemon: List<PokemonTypeEntry>
)

data class PokemonTypeEntry(
    val pokemon: NamedApiResource,
    val slot: Int
)

data class TypeDetailResponse(
    val id: Int,
    val name: String,
    val pokemon: List<PokemonWrapper>
)

data class PokemonWrapper(
    val pokemon: NamedApiResource,
    val slot: Int
)
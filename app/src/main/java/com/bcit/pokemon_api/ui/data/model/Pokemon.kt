package com.bcit.pokemon_api.ui.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: Int = 0,
    val weight: Int = 0,
    val imageUrl: String,
    val types: String = ""
)

// Keep these separate from the Room entity
data class PokemonApiResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<TypeSlot>
)

data class Sprites(
    val front_default: String
)

data class TypeSlot(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String
)
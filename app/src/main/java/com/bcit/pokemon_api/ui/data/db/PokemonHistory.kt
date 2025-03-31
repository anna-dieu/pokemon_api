package com.bcit.pokemon_api.ui.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_history")
data class PokemonHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pokemonId: Int,
    val name: String,
    val imageUrl: String,
    val viewedAt: Long = System.currentTimeMillis()
)
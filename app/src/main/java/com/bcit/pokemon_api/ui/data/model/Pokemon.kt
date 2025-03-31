package com.bcit.pokemon_api.ui.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String
)
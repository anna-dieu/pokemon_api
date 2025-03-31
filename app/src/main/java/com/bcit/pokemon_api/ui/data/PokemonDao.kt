package com.bcit.pokemon_api.ui.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bcit.pokemon_api.ui.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon ORDER BY id DESC")
    fun getAllPokemon(): Flow<List<Pokemon>>
    
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonById(id: Int): Pokemon?
}
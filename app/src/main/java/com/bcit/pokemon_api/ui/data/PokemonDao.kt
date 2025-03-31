package com.bcit.pokemon_api.ui.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.bcit.pokemon_api.ui.data.model.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon ORDER BY id DESC")
    fun getAllHistory(): Flow<List<Pokemon>>
}
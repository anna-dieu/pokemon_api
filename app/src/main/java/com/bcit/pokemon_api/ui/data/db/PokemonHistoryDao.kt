package com.bcit.pokemon_api.ui.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: PokemonHistory)

    @Query("SELECT * FROM pokemon_history ORDER BY viewedAt DESC")
    fun getAllHistory(): Flow<List<PokemonHistory>>
}
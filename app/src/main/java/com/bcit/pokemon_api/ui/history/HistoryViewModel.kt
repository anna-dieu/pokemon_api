package com.bcit.pokemon_api.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bcit.pokemon_api.ui.data.db.PokemonHistory
import com.bcit.pokemon_api.ui.data.db.PokemonHistoryDao

class HistoryViewModel(private val historyDao: PokemonHistoryDao) : ViewModel() {
    val allHistory: LiveData<List<PokemonHistory>> = historyDao.getAllHistory().asLiveData()
}

// Factory class in the same file
class HistoryViewModelFactory(private val historyDao: PokemonHistoryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(historyDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
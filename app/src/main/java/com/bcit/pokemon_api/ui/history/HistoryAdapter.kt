package com.bcit.pokemon_api.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bcit.pokemon_api.databinding.ItemPokemonHistoryBinding
import com.bcit.pokemon_api.ui.data.db.PokemonHistory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private val historyItems = mutableListOf<PokemonHistory>()

    inner class HistoryViewHolder(private val binding: ItemPokemonHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: PokemonHistory) {
            binding.pokemonName.text = history.name.replaceFirstChar { 
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() 
            }
            binding.pokemonId.text = "ID: ${history.pokemonId}"
            
            val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            binding.viewedAt.text = "Viewed: ${dateFormat.format(Date(history.viewedAt))}"
            
            binding.pokemonImage.load(history.imageUrl) {
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemPokemonHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun getItemCount() = historyItems.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyItems[position])
    }

    fun updateHistory(newItems: List<PokemonHistory>) {
        historyItems.clear()
        historyItems.addAll(newItems)
        notifyDataSetChanged()
    }
}
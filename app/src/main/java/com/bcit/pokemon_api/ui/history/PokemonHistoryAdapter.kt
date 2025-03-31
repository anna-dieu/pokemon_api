package com.bcit.pokemon_api.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bcit.pokemon_api.databinding.ItemPokemonHistoryBinding
import com.bcit.pokemon_api.ui.data.db.PokemonHistory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PokemonHistoryAdapter : ListAdapter<PokemonHistory, PokemonHistoryAdapter.ViewHolder>(PokemonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokemonHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
    }

    class ViewHolder(private val binding: ItemPokemonHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: PokemonHistory) {
            binding.pokemonName.text = history.name.replaceFirstChar { it.uppercase() }
            binding.pokemonId.text = "ID: ${history.pokemonId}"
            
            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            binding.viewedAt.text = "Viewed on: ${sdf.format(Date(history.viewedAt))}"
            
            binding.pokemonImage.load(history.imageUrl) {
                crossfade(true)
            }
        }
    }

    private class PokemonDiffCallback : DiffUtil.ItemCallback<PokemonHistory>() {
        override fun areItemsTheSame(oldItem: PokemonHistory, newItem: PokemonHistory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokemonHistory, newItem: PokemonHistory): Boolean {
            return oldItem == newItem
        }
    }
}
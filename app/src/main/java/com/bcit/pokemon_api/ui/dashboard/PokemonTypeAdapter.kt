package com.bcit.pokemon_api.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bcit.pokemon_api.databinding.ItemPokemonTypeBinding
import com.bcit.pokemon_api.ui.data.model.PokemonType

class PokemonTypeAdapter(
    private val pokemonTypes: List<PokemonType>,
    private val onTypeClicked: (String) -> Unit
) : RecyclerView.Adapter<PokemonTypeAdapter.TypeViewHolder>() {

    inner class TypeViewHolder(private val binding: ItemPokemonTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(type: PokemonType) {
            binding.typeName.text = type.displayName
            binding.typeCard.setCardBackgroundColor(type.color)
            // Removed reference to typeIcon which no longer exists
            
            binding.root.setOnClickListener {
                onTypeClicked(type.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val binding = ItemPokemonTypeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TypeViewHolder(binding)
    }

    override fun getItemCount() = pokemonTypes.size

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        holder.bind(pokemonTypes[position])
    }
}
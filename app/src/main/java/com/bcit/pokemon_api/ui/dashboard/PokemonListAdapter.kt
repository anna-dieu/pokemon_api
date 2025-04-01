package com.bcit.pokemon_api.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bcit.pokemon_api.databinding.ItemPokemonListBinding
import com.bcit.pokemon_api.ui.data.model.Pokemon
import java.util.Locale

class PokemonListAdapter(
    private val pokemonList: List<Pokemon>,
    private val onPokemonClicked: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(private val binding: ItemPokemonListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.pokemonName.text = pokemon.name.replaceFirstChar { 
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() 
            }
            binding.pokemonId.text = "ID: ${pokemon.id}"
            binding.pokemonTypes.text = "Types: ${pokemon.types}"
            
            binding.pokemonImage.load(pokemon.imageUrl) {
                crossfade(true)
            }
            
            binding.root.setOnClickListener {
                onPokemonClicked(pokemon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonViewHolder(binding)
    }

    override fun getItemCount() = pokemonList.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }
}
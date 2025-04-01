package com.bcit.pokemon_api.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.bcit.pokemon_api.R
import com.bcit.pokemon_api.databinding.FragmentPokemonDetailBinding
import java.util.Locale

class PokemonDetailFragment : Fragment() {
    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PokemonDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        
        // Get the pokemon ID from arguments (default to 1 if not provided)
        val pokemonId = arguments?.getInt("pokemonId") ?: 1
        
        // Initialize ViewModel with the pokemon ID
        val factory = PokemonDetailViewModelFactory(requireActivity().application, pokemonId)
        viewModel = ViewModelProvider(this, factory)[PokemonDetailViewModel::class.java]
        
        setupObservers()
        
        return binding.root
    }
    
    private fun setupObservers() {
        viewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
            if (pokemon != null) {
                // Set pokemon name with first letter capitalized
                binding.pokemonName.text = pokemon.name.replaceFirstChar { 
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() 
                }
                
                // Set pokemon ID
                binding.pokemonId.text = "ID: ${pokemon.id}"
                
                // Set pokemon image
                binding.pokemonImage.load(pokemon.imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_home_black_24dp)
                }
                
                // Set pokemon height and weight
                binding.pokemonHeight.text = "Height: ${pokemon.height/10.0}m"
                binding.pokemonWeight.text = "Weight: ${pokemon.weight/10.0}kg"
                
                // Set pokemon types
                binding.pokemonTypes.text = "Types: ${pokemon.types}"
            }
        }
        
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (!errorMsg.isNullOrEmpty()) {
                binding.errorText.text = errorMsg
                binding.errorText.visibility = View.VISIBLE
            } else {
                binding.errorText.visibility = View.GONE
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.bcit.pokemon_api.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.bcit.pokemon_api.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set title to "Random Pokemon"
        binding.titlePokemonOfDay.text = "Random Pokemon"

        // Add padding to ensure text isn't cut off
        binding.titlePokemonOfDay.setPadding(16, 16, 16, 16)

        // Adjust text size for better readability
        binding.pokemonName.textSize = 20f

        binding.loadRandomButton.setOnClickListener {
            homeViewModel.loadRandomPokemon()
        }

        homeViewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
            if (pokemon != null) {
                binding.pokemonName.text = pokemon.name.capitalize()
                binding.pokemonId.text = "ID: ${pokemon.id}"
                binding.pokemonWeight.text = "Weight: ${pokemon.weight / 10.0}kg"
                binding.pokemonHeight.text = "Height: ${pokemon.height / 10.0}m"
                binding.pokemonTypes.text = "Type: ${pokemon.types}"

                binding.pokemonImage.load(pokemon.imageUrl) {
                    crossfade(true)
                }

                binding.contentGroup.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.errorText.visibility = View.GONE
            }
        }

        homeViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.contentGroup.visibility = View.GONE
                binding.errorText.visibility = View.GONE
            }
        }

        homeViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (!errorMsg.isNullOrEmpty()) {
                binding.errorText.text = errorMsg
                binding.errorText.visibility = View.VISIBLE
                binding.contentGroup.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

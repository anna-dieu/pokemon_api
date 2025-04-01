package com.bcit.pokemon_api.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcit.pokemon_api.databinding.FragmentTypeDetailBinding
import com.bcit.pokemon_api.ui.data.model.Pokemon

class TypeDetailFragment : Fragment() {
    private var _binding: FragmentTypeDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TypeDetailViewModel
    private lateinit var pokemonAdapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTypeDetailBinding.inflate(inflater, container, false)
        
        // Get type name from arguments (fallback to "normal")
        val typeName = arguments?.getString("typeName") ?: "normal"
        
        // Set the title to display the type name
        binding.titleType.text = "Type: ${typeName.capitalize()}"
        
        // Create the ViewModel with the type name
        val factory = TypeDetailViewModelFactory(requireActivity().application, typeName)
        viewModel = ViewModelProvider(this, factory)[TypeDetailViewModel::class.java]
        
        // Set up RecyclerView
        setupRecyclerView()
        
        // Observe ViewModel
        observeViewModel()
        
        return binding.root
    }

    private fun setupRecyclerView() {
        pokemonAdapter = PokemonListAdapter(emptyList()) { pokemon ->
            navigateToPokemonDetail(pokemon)
        }
        
        binding.recyclerViewPokemon.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pokemonAdapter
        }
    }
    
    private fun observeViewModel() {
        viewModel.pokemonList.observe(viewLifecycleOwner) { pokemonList ->
            if (pokemonList.isEmpty()) {
                binding.textViewNoPokemon.visibility = View.VISIBLE
                binding.recyclerViewPokemon.visibility = View.GONE
            } else {
                binding.textViewNoPokemon.visibility = View.GONE
                binding.recyclerViewPokemon.visibility = View.VISIBLE
                pokemonAdapter = PokemonListAdapter(pokemonList) { pokemon ->
                    navigateToPokemonDetail(pokemon)
                }
                binding.recyclerViewPokemon.adapter = pokemonAdapter
            }
        }
        
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (errorMsg.isNotEmpty()) {
                binding.textViewError.text = errorMsg
                binding.textViewError.visibility = View.VISIBLE
            } else {
                binding.textViewError.visibility = View.GONE
            }
        }
    }
    
    private fun navigateToPokemonDetail(pokemon: Pokemon) {
        // Implement navigation later when we have the full navigation flow
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
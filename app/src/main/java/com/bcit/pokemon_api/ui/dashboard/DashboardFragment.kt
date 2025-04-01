package com.bcit.pokemon_api.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bcit.pokemon_api.R
import com.bcit.pokemon_api.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var typeAdapter: PokemonTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeViewModel()

        return root
    }

    private fun setupRecyclerView() {
        typeAdapter = PokemonTypeAdapter(emptyList()) { typeName ->
            navigateToTypeDetail(typeName)
        }
        
        binding.recyclerViewTypes.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = typeAdapter
        }
    }

    private fun navigateToTypeDetail(typeName: String) {
        // Use a simpler navigation approach with a Bundle
        val bundle = Bundle().apply {
            putString("typeName", typeName)
        }
        findNavController().navigate(R.id.action_navigation_dashboard_to_type_detail, bundle)
    }
    
    private fun observeViewModel() {
        dashboardViewModel.pokemonTypes.observe(viewLifecycleOwner) { types ->
            typeAdapter = PokemonTypeAdapter(types) { typeName ->
                navigateToTypeDetail(typeName)
            }
            binding.recyclerViewTypes.adapter = typeAdapter
        }

        dashboardViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        dashboardViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            binding.errorText.apply {
                text = errorMsg
                visibility = if (!errorMsg.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
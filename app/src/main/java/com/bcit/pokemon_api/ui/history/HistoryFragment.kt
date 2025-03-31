package com.bcit.pokemon_api.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcit.pokemon_api.databinding.FragmentHistoryBinding
import com.bcit.pokemon_api.ui.data.db.PokemonDatabase

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        
        // Create the adapter
        historyAdapter = HistoryAdapter()
        setupRecyclerView()
        
        // Initialize ViewModel
        val database = PokemonDatabase.getInstance(requireContext())
        val dao = database.pokemonHistoryDao
        val viewModelFactory = HistoryViewModelFactory(dao)
        historyViewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]
        
        observeViewModel()
        
        return binding.root
    }
    
    private fun setupRecyclerView() {
        binding.recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }
    }
    
    private fun observeViewModel() {
        historyViewModel.allHistory.observe(viewLifecycleOwner) { history ->
            if (history.isEmpty()) {
                binding.textViewNoHistory.visibility = View.VISIBLE
                binding.recyclerViewHistory.visibility = View.GONE
            } else {
                binding.textViewNoHistory.visibility = View.GONE
                binding.recyclerViewHistory.visibility = View.VISIBLE
                historyAdapter.updateHistory(history)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
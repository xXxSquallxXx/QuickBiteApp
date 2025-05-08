package com.example.quickbiteapp.ui.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickbiteapp.databinding.FragmentRestaurantListBinding
import com.example.quickbiteapp.di.MainApplication
import com.example.quickbiteapp.ui.adapter.RestaurantAdapter
import kotlinx.coroutines.launch

class RestaurantListFragment : Fragment() {

    var viewModel: RestaurantViewModel? = null
    var restaurantAdapter: RestaurantAdapter? = null

    private var binding: FragmentRestaurantListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel == null || restaurantAdapter == null) {
            val appComponent = (requireActivity().application as MainApplication).appComponent
            val factory = appComponent.restaurantListFragmentFactory()
            viewModel = factory.viewModel
            restaurantAdapter = factory.restaurantAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        if (viewModel != null && restaurantAdapter != null)
            observeRestaurants()
    }

    private fun setupRecyclerView() {
        binding?.restaurantList?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = restaurantAdapter
        }
    }

    private fun observeRestaurants() {
        lifecycleScope.launch {
            viewModel?.restaurants?.collect { restaurants ->
                restaurantAdapter?.submitList(restaurants)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
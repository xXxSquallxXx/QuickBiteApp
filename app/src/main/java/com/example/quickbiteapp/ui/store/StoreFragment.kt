package com.example.quickbiteapp.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickbiteapp.data.model.Product
import com.example.quickbiteapp.databinding.FragmentStoreBinding
import com.example.quickbiteapp.ui.adapter.ProductAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoreFragment @Inject constructor(
    internal val viewModel: StoreViewModel?
) : Fragment() {

    private var binding: FragmentStoreBinding? = null
    private val productAdapter = ProductAdapter { product -> onAddToCart(product) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeProducts()
    }

    private fun setupRecyclerView() {
        binding?.productList?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    private fun observeProducts() {
        lifecycleScope.launch {
            viewModel?.products?.collect { products ->
                productAdapter.submitList(products)
            }
        }
    }

    private fun onAddToCart(product: Product) {
        viewModel?.addToCart(product)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
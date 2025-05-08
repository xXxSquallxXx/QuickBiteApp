package com.example.quickbiteapp.ui.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickbiteapp.databinding.FragmentCartBinding
import com.example.quickbiteapp.di.MainApplication
import com.example.quickbiteapp.ui.adapter.CartAdapter
import kotlinx.coroutines.launch
import androidx.core.content.edit

class CartFragment : Fragment() {

    var viewModel: CartViewModel? = null
    var cartAdapter: CartAdapter? = null

    private var binding: FragmentCartBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel == null || cartAdapter == null) {
            val appComponent = (requireActivity().application as MainApplication).appComponent
            val factory = appComponent.cartFragmentFactory()
            viewModel = factory.viewModel
            cartAdapter = factory.cartAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeCartItems()
        setupClearCartButton()

        val prefs = requireContext().getSharedPreferences("CartPrefs", Context.MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("isFirstRun", true)
        if (isFirstRun) {
            viewModel?.clearCart()
            prefs.edit { putBoolean("isFirstRun", false) }
        }
    }

    private fun setupRecyclerView() {
        binding?.cartList?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun setupClearCartButton() {
        binding?.clearCartButton?.setOnClickListener {
            viewModel?.clearCart()
        }
    }

    private fun observeCartItems() {
        lifecycleScope.launch {
            viewModel?.cartItems?.collect { items ->
                cartAdapter?.submitList(items)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
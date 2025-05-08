package com.example.quickbiteapp.ui.cart

import com.example.quickbiteapp.ui.adapter.CartAdapter
import javax.inject.Inject

class CartFragmentFactory @Inject constructor(
    internal val viewModel: CartViewModel,
    internal val cartAdapter: CartAdapter
) {
    fun create(): CartFragment {
        return CartFragment().apply {
            this.viewModel = viewModel
            this.cartAdapter = cartAdapter
        }
    }
}
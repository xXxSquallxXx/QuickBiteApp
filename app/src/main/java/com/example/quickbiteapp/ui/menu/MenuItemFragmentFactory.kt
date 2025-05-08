package com.example.quickbiteapp.ui.menu

import com.example.quickbiteapp.data.repository.CartRepository
import javax.inject.Inject

class MenuItemFragmentFactory @Inject constructor(
    internal val cartRepository: CartRepository
) {
    fun create(): MenuItemFragment {
        return MenuItemFragment().apply {
            this.cartRepository = cartRepository
        }
    }
}
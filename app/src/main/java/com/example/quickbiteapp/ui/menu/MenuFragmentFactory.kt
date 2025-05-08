package com.example.quickbiteapp.ui.menu

import com.example.quickbiteapp.ui.adapter.MenuAdapter
import javax.inject.Inject

class MenuFragmentFactory @Inject constructor(
    internal val viewModel: MenuViewModel,
    internal val menuAdapter: MenuAdapter
) {
    fun create(): MenuFragment {
        return MenuFragment().apply {
            this.viewModel = viewModel
            this.menuAdapter = menuAdapter
        }
    }
}
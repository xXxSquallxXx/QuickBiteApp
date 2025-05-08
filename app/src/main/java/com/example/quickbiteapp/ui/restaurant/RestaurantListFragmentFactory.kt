package com.example.quickbiteapp.ui.restaurant

import com.example.quickbiteapp.ui.adapter.RestaurantAdapter
import javax.inject.Inject

class RestaurantListFragmentFactory @Inject constructor(
    internal val viewModel: RestaurantViewModel,
    internal val restaurantAdapter: RestaurantAdapter
) {
    fun create(): RestaurantListFragment {
        return RestaurantListFragment().apply {
            this.viewModel = viewModel
            this.restaurantAdapter = restaurantAdapter
        }
    }
}
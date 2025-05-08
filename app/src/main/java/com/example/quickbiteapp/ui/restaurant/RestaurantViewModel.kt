package com.example.quickbiteapp.ui.restaurant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbiteapp.data.api.RestaurantApi
import com.example.quickbiteapp.data.model.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestaurantViewModel @Inject constructor(
    private val restaurantApi: RestaurantApi
) : ViewModel() {

    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> get() = _restaurants

    init {
        loadRestaurants()
    }

    private fun loadRestaurants() {
        viewModelScope.launch {
            try {
                val restaurantList = restaurantApi.getRestaurants()
                Log.d("Test", "Рестораны получены: $restaurantList")
                _restaurants.value = restaurantList
            } catch (e: Exception) {
                Log.e("Test", "Ошибка загрузки ресторанов: ${e.message}")
                _restaurants.value = emptyList()
            }
        }
    }
}
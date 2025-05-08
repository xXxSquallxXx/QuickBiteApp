package com.example.quickbiteapp.ui.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbiteapp.data.api.MenuApi
import com.example.quickbiteapp.data.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val menuApi: MenuApi
) : ViewModel() {

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> get() = _menuItems

    private var currentRestaurantId: Int? = null

    fun loadMenu(restaurantId: Int) {
        if (currentRestaurantId != restaurantId || _menuItems.value.isEmpty()) {
            currentRestaurantId = restaurantId
            viewModelScope.launch {
                try {
                    val menuList = menuApi.getMenu(restaurantId)
                    Log.d("Test", "Меню для restaurantId $restaurantId успешно загружено: $menuList")
                    _menuItems.value = menuList
                } catch (e: Exception) {
                    Log.e("Test", "Ошибка загрузки меню для restaurantId $restaurantId: ${e.message}")
                    _menuItems.value = emptyList()
                }
            }
        }
    }
}
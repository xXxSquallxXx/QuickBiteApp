package com.example.quickbiteapp.ui.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbiteapp.data.model.CartItem
import com.example.quickbiteapp.data.model.Product
import com.example.quickbiteapp.data.repository.CartRepository
import com.example.quickbiteapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoreViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    init {
        viewModelScope.launch {
            productRepository.getAllProducts().collect { productList ->
                _products.value = productList
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.insert(CartItem(restaurantId = 0, productId = product.id, quantity = 1, name = product.name))
        }
    }
}
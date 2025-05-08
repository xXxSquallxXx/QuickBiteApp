package com.example.quickbiteapp.data.repository

import com.example.quickbiteapp.data.database.CartDao
import com.example.quickbiteapp.data.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class CartRepository(private val cartDao: CartDao) {

    fun getAllCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems()
    }

    suspend fun insert(cartItem: CartItem) {
        val existingItem = cartDao.getAllCartItems().firstOrNull()
            ?.find { it.productId == cartItem.productId && it.restaurantId == cartItem.restaurantId }
        if (existingItem != null) {
            cartDao.updateQuantity(existingItem.id, existingItem.quantity + cartItem.quantity)
        } else {
            cartDao.insert(cartItem)
        }
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}
package com.example.quickbiteapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quickbiteapp.data.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT cart_item.id, cart_item.restaurantId, cart_item.productId, cart_item.quantity, cart_item.name FROM cart_item")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Insert
    suspend fun insert(cartItem: CartItem)

    @Query("UPDATE cart_item SET quantity = :quantity WHERE id = :id")
    suspend fun updateQuantity(id: Int, quantity: Int)

    @Query("DELETE FROM cart_item")
    suspend fun clearCart()
}
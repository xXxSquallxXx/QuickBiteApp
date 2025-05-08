package com.example.quickbiteapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val restaurantId: Int,
    val productId: Int,
    val quantity: Int,
    val name: String? = null
)
package com.example.quickbiteapp.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.quickbiteapp.data.model.CartItem
import com.example.quickbiteapp.data.model.MenuItem
import com.example.quickbiteapp.data.model.Product
import com.example.quickbiteapp.data.model.Restaurant

abstract class DiffUtilCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}

object CartDiffUtilCallback : DiffUtilCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.id == newItem.id
    }
}

object ProductDiffUtilCallback : DiffUtilCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }
}

object RestaurantDiffUtilCallback : DiffUtilCallback<Restaurant>() {
    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem.id == newItem.id
    }
}

object MenuDiffUtilCallback : DiffUtilCallback<MenuItem>() {
    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem.id == newItem.id
    }
}
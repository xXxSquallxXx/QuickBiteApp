package com.example.quickbiteapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quickbiteapp.R
import com.example.quickbiteapp.data.model.CartItem
import com.example.quickbiteapp.databinding.ItemCartBinding
import com.example.quickbiteapp.util.CartDiffUtilCallback

class CartAdapter : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            binding.cartItemId.text = cartItem.name ?: binding.root.context.getString(R.string.unknown_item)
            binding.cartItemQuantity.text = binding.root.context.getString(R.string.cart_item_quantity, cartItem.quantity)
        }
    }
}
package com.example.quickbiteapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.quickbiteapp.R
import com.example.quickbiteapp.data.model.MenuItem
import com.example.quickbiteapp.databinding.ItemMenuBinding
import com.example.quickbiteapp.di.MainApplication
import com.example.quickbiteapp.ui.navigation.NavigationManager
import com.example.quickbiteapp.util.MenuDiffUtilCallback

class MenuAdapter : ListAdapter<MenuItem, MenuAdapter.MenuViewHolder>(MenuDiffUtilCallback) {

    private var restaurantId: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setRestaurantId(id: Int) {
        this.restaurantId = id
    }

    inner class MenuViewHolder(private val binding: ItemMenuBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: MenuItem) {
            binding.menuItemName.text = menuItem.name
            binding.menuItemDescription.text = menuItem.description
            binding.menuItemPrice.text = binding.root.context.getString(R.string.product_price_format, menuItem.price)
            binding.menuItemImage.load(menuItem.imageUrl)

            binding.root.setOnClickListener {
                val appComponent = (binding.root.context.applicationContext as MainApplication).appComponent
                val menuItemFragment = appComponent.menuItemFragmentFactory().create()
                menuItemFragment.setMenuItem(menuItem, restaurantId)
                NavigationManager.replaceFragment(menuItemFragment)
            }
        }
    }
}
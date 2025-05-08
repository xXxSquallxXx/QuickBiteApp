package com.example.quickbiteapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.quickbiteapp.data.model.Restaurant
import com.example.quickbiteapp.databinding.ItemRestaurantBinding
import com.example.quickbiteapp.di.MainApplication
import com.example.quickbiteapp.ui.navigation.NavigationManager
import com.example.quickbiteapp.util.RestaurantDiffUtilCallback

class RestaurantAdapter : ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder>(RestaurantDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RestaurantViewHolder(private val binding: ItemRestaurantBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.restaurantName.text = restaurant.name
            binding.restaurantAddress.text = restaurant.address
            binding.restaurantRating.text = restaurant.rating.toString()
            binding.restaurantImage.load(restaurant.imageUrl)

            binding.root.setOnClickListener {
                val appComponent = (binding.root.context.applicationContext as MainApplication).appComponent
                val menuFragment = appComponent.menuFragmentFactory().create()
                menuFragment.setRestaurantId(restaurant.id)
                NavigationManager.replaceFragment(menuFragment)
            }
        }
    }
}
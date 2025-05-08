package com.example.quickbiteapp.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.quickbiteapp.R
import com.example.quickbiteapp.data.model.CartItem
import com.example.quickbiteapp.data.model.MenuItem
import com.example.quickbiteapp.data.repository.CartRepository
import com.example.quickbiteapp.databinding.FragmentMenuItemBinding
import com.example.quickbiteapp.di.MainApplication
import kotlinx.coroutines.launch

class MenuItemFragment : Fragment() {

    var cartRepository: CartRepository? = null

    private var binding: FragmentMenuItemBinding? = null
    private var menuItem: MenuItem? = null
    private var restaurantId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (cartRepository == null) {
            val appComponent = (requireActivity().application as MainApplication).appComponent
            val factory = appComponent.menuItemFragmentFactory()
            cartRepository = factory.cartRepository
        }

        if (savedInstanceState != null) {
            restaurantId = savedInstanceState.getInt("restaurantId")
            val id = savedInstanceState.getInt("menuItemId")
            val name = savedInstanceState.getString("menuItemName")
            val description = savedInstanceState.getString("menuItemDescription")
            val price = savedInstanceState.getDouble("menuItemPrice")
            val imageUrl = savedInstanceState.getString("menuItemImageUrl")
            if (name != null && description != null && imageUrl != null) {
                menuItem = MenuItem(id, name, description, price, imageUrl)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuItemBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.toolbar?.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        menuItem?.let { item ->
            binding?.menuItemImage?.load(item.imageUrl)
            binding?.menuItemName?.text = item.name
            binding?.menuItemDescription?.text = item.description
            binding?.menuItemPrice?.text = String.format(
                getString(R.string.product_price_format),
                item.price
            )
        }

        binding?.addToCartButton?.setOnClickListener {
            menuItem?.let { item ->
                lifecycleScope.launch {
                    val uniqueProductId = restaurantId * 100 + item.id
                    cartRepository?.insert(CartItem(restaurantId = restaurantId, productId = uniqueProductId, quantity = 1, name = item.name))
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("restaurantId", restaurantId)
        menuItem?.let {
            outState.putInt("menuItemId", it.id)
            outState.putString("menuItemName", it.name)
            outState.putString("menuItemDescription", it.description)
            outState.putDouble("menuItemPrice", it.price)
            outState.putString("menuItemImageUrl", it.imageUrl)
        }
    }

    fun setMenuItem(item: MenuItem, restaurantId: Int) {
        this.menuItem = item
        this.restaurantId = restaurantId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
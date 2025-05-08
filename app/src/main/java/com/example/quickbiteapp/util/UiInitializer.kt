package com.example.quickbiteapp.util

import androidx.fragment.app.FragmentActivity
import com.example.quickbiteapp.databinding.ActivityMainBinding
import com.example.quickbiteapp.di.AppComponent
import com.example.quickbiteapp.ui.navigation.NavigationManager
import com.example.quickbiteapp.R
import com.example.quickbiteapp.ui.cart.CartFragment
import com.example.quickbiteapp.ui.menu.MenuFragment
import com.example.quickbiteapp.ui.menu.MenuItemFragment
import com.example.quickbiteapp.ui.restaurant.RestaurantListFragment

object UiInitializer {
    fun initUi(activity: FragmentActivity, binding: ActivityMainBinding, appComponent: AppComponent) {
        NavigationManager.setFragmentManager(activity.supportFragmentManager)
        setupBottomNavigation(activity, binding, appComponent)
        setupBackStackListener(activity, binding)
        NavigationManager.replaceFragmentForRestaurant(appComponent.restaurantListFragmentFactory().create())
    }

    internal fun setupBottomNavigation(activity: FragmentActivity, binding: ActivityMainBinding, appComponent: AppComponent) {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val currentFragment = getCurrentFragment(activity)
            when (item.itemId) {
                R.id.nav_store -> {
                    if (!isStoreRelatedFragment(currentFragment)) {
                        NavigationManager.replaceFragmentForRestaurant(appComponent.restaurantListFragmentFactory().create())
                    }
                    true
                }
                R.id.nav_cart -> {
                    if (currentFragment !is CartFragment) {
                        NavigationManager.replaceFragmentForRestaurant(appComponent.cartFragmentFactory().create())
                    }
                    true
                }
                else -> false
            }
        }
    }

    internal fun setupBackStackListener(activity: FragmentActivity, binding: ActivityMainBinding) {
        activity.supportFragmentManager.addOnBackStackChangedListener {
            NavigationManager.onBackStackChanged()
            val currentFragment = getCurrentFragment(activity)
            binding.bottomNavigation.selectedItemId = when (currentFragment) {
                is RestaurantListFragment -> R.id.nav_store
                is MenuFragment -> R.id.nav_store
                is MenuItemFragment -> R.id.nav_store
                is CartFragment -> R.id.nav_cart
                else -> R.id.nav_store
            }
        }
    }

    private fun getCurrentFragment(activity: FragmentActivity): androidx.fragment.app.Fragment? {
        return activity.supportFragmentManager.fragments.lastOrNull()
    }

    private fun isStoreRelatedFragment(fragment: androidx.fragment.app.Fragment?): Boolean {
        return fragment is RestaurantListFragment ||
                fragment is MenuFragment ||
                fragment is MenuItemFragment
    }
}
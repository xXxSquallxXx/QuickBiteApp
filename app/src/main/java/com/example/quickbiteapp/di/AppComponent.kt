package com.example.quickbiteapp.di

import com.example.quickbiteapp.ui.adapter.CartAdapter
import com.example.quickbiteapp.ui.cart.CartFragment
import com.example.quickbiteapp.ui.cart.CartFragmentFactory
import com.example.quickbiteapp.ui.menu.MenuFragment
import com.example.quickbiteapp.ui.menu.MenuFragmentFactory
import com.example.quickbiteapp.ui.menu.MenuItemFragment
import com.example.quickbiteapp.ui.menu.MenuItemFragmentFactory
import com.example.quickbiteapp.ui.restaurant.RestaurantListFragment
import com.example.quickbiteapp.ui.restaurant.RestaurantListFragmentFactory
import com.example.quickbiteapp.ui.store.StoreFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun storeFragment(): StoreFragment
    fun restaurantListFragmentFactory(): RestaurantListFragmentFactory
    fun menuFragmentFactory(): MenuFragmentFactory
    fun cartFragmentFactory(): CartFragmentFactory
    fun menuItemFragmentFactory(): MenuItemFragmentFactory
    fun cartAdapter(): CartAdapter

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MainApplication): Builder
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}
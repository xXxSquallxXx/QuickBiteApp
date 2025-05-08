package com.example.quickbiteapp.di

import android.content.Context
import android.util.Log
import com.example.quickbiteapp.data.api.ApiUrls
import com.example.quickbiteapp.data.api.MenuApi
import com.example.quickbiteapp.data.api.RestaurantApi
import com.example.quickbiteapp.data.database.AppDatabase
import com.example.quickbiteapp.data.repository.CartRepository
import com.example.quickbiteapp.data.repository.ProductRepository
import com.example.quickbiteapp.data.model.MenuItem
import com.example.quickbiteapp.ui.adapter.CartAdapter
import com.example.quickbiteapp.ui.adapter.MenuAdapter
import com.example.quickbiteapp.ui.adapter.RestaurantAdapter
import com.example.quickbiteapp.ui.cart.CartFragmentFactory
import com.example.quickbiteapp.ui.menu.MenuFragmentFactory
import com.example.quickbiteapp.ui.menu.MenuItemFragmentFactory
import com.example.quickbiteapp.ui.restaurant.RestaurantListFragmentFactory
import com.example.quickbiteapp.ui.restaurant.RestaurantViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideProductRepository(database: AppDatabase): ProductRepository {
        return ProductRepository(database.productDao())
    }

    @Provides
    fun provideCartRepository(database: AppDatabase): CartRepository {
        return CartRepository(database.cartDao())
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiUrls.RESTAURANTS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRestaurantApi(retrofit: Retrofit): RestaurantApi {
        return retrofit.create(RestaurantApi::class.java)
    }

    @Provides
    fun provideMenuApi(): MenuApi {
        return object : MenuApi {
            override suspend fun getMenu(restaurantId: Int): List<MenuItem> {
                val url = ApiUrls.MENU_URLS[restaurantId] ?: throw IllegalArgumentException("Menu not found for restaurant $restaurantId")
                Log.d("Test", "Попытка запроса меню для restaurantId: $restaurantId, URL: $url")
                try {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val response = retrofit.create(MenuApi::class.java).getMenu(restaurantId)
                    Log.d("Test", "API запрос успешен: $response")
                    return response
                } catch (e: Exception) {
                    Log.e("Test", "Ошибка API запроса: ${e.message}")
                    throw e
                }
            }
        }
    }

    @Provides
    fun provideRestaurantViewModel(restaurantApi: RestaurantApi): RestaurantViewModel {
        return RestaurantViewModel(restaurantApi)
    }

    @Provides
    fun provideCartAdapter(): CartAdapter {
        return CartAdapter()
    }

    @Provides
    fun provideMenuAdapter(): MenuAdapter {
        return MenuAdapter()
    }

    @Provides
    fun provideRestaurantAdapter(): RestaurantAdapter {
        return RestaurantAdapter()
    }
}
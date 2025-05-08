package com.example.quickbiteapp.data.api

import com.example.quickbiteapp.data.model.Restaurant
import retrofit2.http.GET

interface RestaurantApi {
    @GET(".")
    suspend fun getRestaurants(): List<Restaurant>
}
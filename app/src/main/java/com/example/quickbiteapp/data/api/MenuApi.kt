package com.example.quickbiteapp.data.api

import com.example.quickbiteapp.data.model.MenuItem
import retrofit2.http.GET
import retrofit2.http.Path

interface MenuApi {
    @GET("menu/{restaurantId}")
    suspend fun getMenu(@Path("restaurantId") restaurantId: Int): List<MenuItem>
}
package com.example.quickbiteapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quickbiteapp.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<Product>>

    @Insert
    suspend fun insert(product: Product)
}
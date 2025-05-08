package com.example.quickbiteapp.data.repository

import com.example.quickbiteapp.data.database.ProductDao
import com.example.quickbiteapp.data.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }
}
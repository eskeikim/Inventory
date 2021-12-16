package com.skimani.inventory.repository

import androidx.lifecycle.LiveData
import com.skimani.inventory.data.entities.Products
import com.skimani.inventory.db.dao.InventoryDao
import javax.inject.Inject

class InventoryRepository @Inject constructor(private val inventoryDao: InventoryDao) {
    /**
     * Fetch All Products
     */
    fun fetchAllProducts(): LiveData<List<Products>> {
        return inventoryDao.fetchAllProducts()
    }

    /**
     * Fetch products filtered by category
     */
    fun filteredProducts(name: String): LiveData<List<Products>> {
        return inventoryDao.filteredProducts(name)
    }

    /**
     * Add a new product to db
     */
    suspend fun addProducts(product: Products) {
        inventoryDao.addProducts(product)
    }
}

package com.skimani.inventory.repository

import androidx.lifecycle.LiveData
import com.skimani.inventory.data.entities.Products
import com.skimani.inventory.db.dao.InventoryDao
import javax.inject.Inject

class InventoryRepository @Inject constructor(private  val inventoryDao: InventoryDao){

    fun fetchAllProducts(): LiveData<List<Products>>{
        return inventoryDao.fetchAllProducts()
    }
    suspend fun addProducts(product: Products){
        inventoryDao.addProducts(product)
    }
}

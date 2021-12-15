package com.skimani.inventory.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skimani.inventory.data.entities.Products

@Dao
interface InventoryDao {
    @Query("SELECT * FROM products")
    fun fetchAllProducts(): LiveData<List<Products>>

    @Query("SELECT * FROM products WHERE category LIKE '%' ||:name|| '%'")
    fun filteredProducts(name: String): LiveData<List<Products>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(products: Products)
}

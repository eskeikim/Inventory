package com.skimani.inventory.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skimani.inventory.data.entities.Products
import com.skimani.inventory.repository.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewmodel @Inject constructor(private val repository: InventoryRepository) : ViewModel() {
    val allProducts: LiveData<List<Products>>
        get() = fetchAllProducts()

    fun filteredProducts(name: String): LiveData<List<Products>> = filteredProduct(name)

    fun fetchAllProducts(): LiveData<List<Products>> {
        return repository.fetchAllProducts()
    }

    fun filteredProduct(name: String): LiveData<List<Products>> {
        return repository.filteredProducts(name)
    }

    fun addProducts(product: Products) {
        viewModelScope.launch {
            repository.addProducts(product)
        }
    }
}

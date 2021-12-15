package com.skimani.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skimani.inventory.data.entities.Products
import com.skimani.inventory.databinding.ProductsItemListBinding

class InventoryAdapter :
    ListAdapter<Products, InventoryAdapter.ProductsAdapterViewholder>(PRODUCTS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapterViewholder {
        return ProductsAdapterViewholder(ProductsItemListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ProductsAdapterViewholder, position: Int) {
        val product = getItem(position)
        holder.bindMessage(product)
    }

    inner class ProductsAdapterViewholder(private val binding: ProductsItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindMessage(products: Products) {
            val displayCode = "(${products.code})- "
            binding.tvName.text = "${products.name} - ${products.type}"
            binding.tvCode.text = displayCode
            binding.tvManufacturer.text = products.manufacturer
            binding.tvUnits.text = products.cost
        }
    }

    companion object {
        val PRODUCTS_COMPARATOR = object : DiffUtil.ItemCallback<Products>() {
            override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
                return oldItem == oldItem
            }
        }
    }
}

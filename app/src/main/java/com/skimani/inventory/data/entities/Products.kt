package com.skimani.inventory.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Products(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "manufacturer") val manufacturer: String,
    @ColumnInfo(name = "distributor") val distributor: String,
    @ColumnInfo(name = "cost") val cost: String,
    @ColumnInfo(name = "retail_price") val retailPrice: String,
    @ColumnInfo(name = "agent_price") val agentPrice: String,
    @ColumnInfo(name = "wholesale_price") val wholesalePrice: String,
    @ColumnInfo(name = "image_path") val imagePath: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}

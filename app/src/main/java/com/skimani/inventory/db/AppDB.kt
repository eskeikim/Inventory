package com.skimani.inventory.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skimani.inventory.data.entities.Products
import com.skimani.inventory.db.dao.InventoryDao

@Database(entities = [Products::class], version = 1, exportSchema = true)
abstract class AppDB : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao

    companion object {
        var INSTANCE: AppDB? = null
        fun getInstance(context: Context): AppDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context)
            }

        private fun buildDatabase(context: Context): AppDB =
            Room.databaseBuilder(context, AppDB::class.java, "inventorydb")
                .build()
    }
}

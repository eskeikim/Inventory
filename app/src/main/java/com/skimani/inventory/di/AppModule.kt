package com.skimani.inventory.di

import android.content.Context
import com.skimani.inventory.db.AppDB
import com.skimani.inventory.db.dao.InventoryDao
import com.skimani.inventory.repository.InventoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesAppDb(@ApplicationContext context: Context): AppDB {
        return AppDB.getInstance(context)
    }

    @Singleton
    @Provides
    fun providesInventoryDao(appDB: AppDB): InventoryDao {
        return appDB.inventoryDao()
    }

    @Singleton
    @Provides
    fun providesInventoryRepository(inventoryDao: InventoryDao): InventoryRepository {
        return InventoryRepository(inventoryDao)
    }
}

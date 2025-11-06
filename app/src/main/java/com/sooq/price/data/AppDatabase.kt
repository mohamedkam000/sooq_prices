package com.sooq.price.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PricesCache::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}
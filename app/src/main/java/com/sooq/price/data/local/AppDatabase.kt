package com.sooq.price.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sooq.price.data.model.ApplePriceEntity

@Database(entities = [ApplePriceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun applePriceDao(): ApplePriceDao
}
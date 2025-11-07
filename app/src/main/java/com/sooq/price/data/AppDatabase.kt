package com.sooq.price.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [PriceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun priceDao(): PriceDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            val inst = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "prices-db").build()
            INSTANCE = inst
            inst
        }
    }
}
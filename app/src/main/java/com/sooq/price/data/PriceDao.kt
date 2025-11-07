package com.sooq.price.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PriceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<PriceEntity>)

    @Query("SELECT value FROM prices WHERE key = :key LIMIT 1")
    suspend fun getValue(key: String): String?

    @Query("SELECT * FROM prices")
    suspend fun getAll(): List<PriceEntity>
}
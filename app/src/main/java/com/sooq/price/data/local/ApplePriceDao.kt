package com.sooq.price.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sooq.price.data.model.ApplePriceEntity

@Dao
interface ApplePriceDao {
    @Query("SELECT * FROM apple_prices")
    suspend fun getAll(): List<ApplePriceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prices: List<ApplePriceEntity>)
}
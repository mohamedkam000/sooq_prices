package com.sooq.price.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CacheDao {
    @Query("SELECT * FROM prices_cache WHERE id = 0 LIMIT 1")
    suspend fun getCache(): PricesCache?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCache(cache: PricesCache)
}
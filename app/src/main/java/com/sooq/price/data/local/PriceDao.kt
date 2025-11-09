package com.sooq.price.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sooq.price.data.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)

    @Query("DELETE FROM items")
    suspend fun clearAllItems()

    @Query("SELECT * FROM items WHERE id = :itemId")
    fun getItemById(itemId: String): Flow<ItemEntity?>
}
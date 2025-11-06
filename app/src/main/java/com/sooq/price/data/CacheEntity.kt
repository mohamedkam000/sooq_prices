package com.sooq.price.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prices_cache")
data class PricesCache(
    @PrimaryKey val id: Int = 0,
    val json: String
)
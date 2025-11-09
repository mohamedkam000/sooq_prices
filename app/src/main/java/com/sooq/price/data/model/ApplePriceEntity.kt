package com.sooq.price.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apple_prices")
data class ApplePriceEntity(
    @PrimaryKey val variety: String,
    val price: Double
)
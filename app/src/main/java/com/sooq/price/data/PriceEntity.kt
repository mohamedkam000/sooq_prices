package com.sooq.price.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prices")
data class PriceEntity(
    @PrimaryKey val key: String,
    val value: String
)
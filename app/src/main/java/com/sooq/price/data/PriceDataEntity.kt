package com.sooq.price.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: String,
    val name: String,
    val img: String,
    val prices: Map<String, Double>,
    val notes: String?,
    val goodsId: String,
    val marketId: String,
    val stateId: String
)
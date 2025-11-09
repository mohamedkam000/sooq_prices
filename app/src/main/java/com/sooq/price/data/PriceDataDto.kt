package com.sooq.price.data

import kotlinx.serialization.Serializable

@Serializable
data class StatesResponse(
    val states: List<StateDto>
)

@Serializable
data class StateDto(
    val id: String,
    val name: String,
    val img: String,
    val markets: List<MarketDto>
)

@Serializable
data class MarketDto(
    val id: String,
    val name: String,
    val img: String,
    val goods: List<GoodsDto>
)

@Serializable
data class GoodsDto(
    val id: String,
    val name: String,
    val img: String,
    val items: List<ItemDto>
)

@Serializable
data class ItemDto(
    val id: String,
    val name: String,
    val img: String,
    val prices: Map<String, Double>? = null,
    val notes: String? = null
)
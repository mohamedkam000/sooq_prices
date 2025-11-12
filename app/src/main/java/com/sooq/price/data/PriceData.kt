package com.sooq.price.data

import kotlinx.serialization.Serializable

@Serializable
data class PriceData(
    val states: List<StateNode> = emptyList()
)

@Serializable
data class StateNode(
    val id: String,
    val name: String,
    val img: String,
    val markets: List<MarketNode> = emptyList()
)

@Serializable
data class MarketNode(
    val id: String,
    val name: String,
    val img: String,
    val goods: List<GoodNode> = emptyList()
)

@Serializable
data class GoodNode(
    val id: String,
    val name: String,
    val img: String,
    val items: List<ItemNode> = emptyList()
)

@Serializable
data class ItemNode(
    val id: String,
    val name: String,
    val img: String,
    val prices: Map<String, Int> = emptyMap()
)
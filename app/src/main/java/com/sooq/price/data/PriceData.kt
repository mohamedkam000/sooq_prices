package com.sooq.price.data

import kotlinx.serialization.Serializable

@Serializable
data class PriceData(
    val states: List<StateNode>
)

@Serializable
data class StateNode(
    val id: String,
    val name: String,
    val img: String,
    val markets: List<MarketNode>
)

@Serializable
data class MarketNode(
    val id: String,
    val name: String,
    val img: String,
    val goods: List<GoodNode>
)

@Serializable
data class GoodNode(
    val id: String,
    val name: String,
    val img: String,
    val items: List<ItemNode>
)

@Serializable
data class ItemNode(
    val id: String,
    val name: String,
    val img: String,
    val prices: Map<String, Int> 
//    val notes: String? = null
)
package com.sooq.price.data.model

data class Root(
    val meta: Meta,
    val states: List<State>
)

data class Meta(
    val lastUpdated: String,
    val source: String
)

data class State(
    val id: String,
    val name: String,
    val img: String,
    val markets: List<Market>
)

data class Market(
    val id: String,
    val name: String,
    val img: String,
    val goods: List<Good>
)

data class Good(
    val id: String,
    val name: String,
    val img: String,
    val items: List<Item>
)

data class Item(
    val id: String,
    val name: String,
    val img: String,
    val prices: Map<String, Double>?,
    val notes: String?
)
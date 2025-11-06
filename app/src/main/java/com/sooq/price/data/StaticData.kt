package com.sooq.price.data

val States = listOf("Khartoum", "Red Sea", "River Nile", "North Kordofan", "Gezira")

val MarketsByState = mapOf(
    "Khartoum" to listOf("Souq Al-Arabi", "Al-Haj Yousif", "Omdurman Central"),
    "Red Sea" to listOf("Port Sudan Main", "Downtown"),
    "River Nile" to listOf("Atbara Market", "Ed Damer Market"),
    "North Kordofan" to listOf("El Obeid Central"),
    "Al-Jazeera" to listOf("Wad Medani Central")
)

val Categories = listOf("Fruits", "Beverages", "Meat")

val ItemsByCategory: Map<String, List<ItemPrice>> = mapOf(
    "Fruits" to listOf(
        ItemPrice("Apples", "apples_s", "apples_m"),
        ItemPrice("Oranges", "oranges_s", "oranges_m"),
        ItemPrice("Grapes", "grapes_s", "grapes_m")
    ),
    "Beverages" to emptyList(),
    "Meat" to emptyList()
)
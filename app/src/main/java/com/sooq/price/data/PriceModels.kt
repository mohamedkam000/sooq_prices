package com.sooq.price.data

import kotlinx.serialization.Serializable

@Serializable
data class PricesDto(
    val oranges_m: String? = null,
    val oranges_s: String? = null,
    val grapes_m: String? = null,
    val grapes_s: String? = null,
    val apples_m: String? = null,
    val apples_s: String? = null
)

enum class Quantity { Single, Large }

data class ItemPrice(
    val name: String,
    val keySingle: String,
    val keyLarge: String
)
package com.sooq.price.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class Converters {
    private val json = Json

    @TypeConverter
    fun fromPriceMap(prices: Map<String, Double>): String {
        return json.encodeToString(prices)
    }

    @TypeConverter
    fun toPriceMap(jsonString: String): Map<String, Double> {
        return json.decodeFromString(json.serializersModule.serializer(), jsonString)
    }
}
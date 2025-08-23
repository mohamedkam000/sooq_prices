package com.sooq.price.model

import org.json.JSONObject
import android.content.Context
import java.io.File

data class MarketData(
    val date: String,
    val prices: Map<String, String>
)

fun loadMarketData(context: Context): MarketData? {
    val file = File(context.filesDir, "data.json")
    if (!file.exists()) return null

    val json = file.readText()
    return try {
        val jsonObject = JSONObject(json)
        val date = jsonObject.getString("date")

        val prices = mutableMapOf<String, String>()
        jsonObject.keys().forEach { key ->
            if (key != "date") {
                prices[key] = jsonObject.getString(key)
            }
        }

        MarketData(date, prices)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
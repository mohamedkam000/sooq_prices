package com.sooq.price.data

import android.app.Application
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

suspend fun loadCachedDate(application: Application): String? {
    return try {
        val cacheFile = File(application.filesDir, "price_data_cache.json")
        if (!cacheFile.exists()) return null
        val cachedText = cacheFile.readText()
        val cachedData = jsonParser.decodeFromString<PriceData>(cachedText)
        cachedData.meta.lastUpdated
    } catch (e: Exception) {
        null
    }
}
package com.sooq.price.data

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class PriceRepository(
    private val client: OkHttpClient = OkHttpClient(),
    private val json: Json = Json { ignoreUnknownKeys = true },
    private val dao: CacheDao
) {
    private val url =
        "https://raw.githubusercontent.com/mohamedkam000/prices/main/data.json"

    suspend fun fetchPrices(): PricesDto? {
        return try {
            val req = Request.Builder().url(url).build()
            client.newCall(req).execute().use { resp ->
                if (!resp.isSuccessful) return loadCache()
                val body = resp.body?.string() ?: return loadCache()
                dao.saveCache(PricesCache(json = body))
                json.decodeFromString(PricesDto.serializer(), body)
            }
        } catch (_: Exception) {
            loadCache()
        }
    }

    private suspend fun loadCache(): PricesDto? {
        val cached = dao.getCache() ?: return null
        return json.decodeFromString(PricesDto.serializer(), cached.json)
    }
}
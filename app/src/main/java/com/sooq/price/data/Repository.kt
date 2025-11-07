package com.sooq.price.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

class Repository(private val context: Context) {
    private val db by lazy { AppDatabase.get(context) }
    private val client: OkHttpClient by lazy {
        val cacheDir = File(context.cacheDir, "http")
        val cache = Cache(cacheDir, 10L * 1024 * 1024) // 10 MB
        OkHttpClient.Builder()
            .cache(cache)
            .build()
    }
    private val api by lazy { PricesApi.create(client) }

    suspend fun refreshAndCache(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val map = api.fetchRaw()
            val list = map.map { PriceEntity(it.key, it.value) }
            db.priceDao().upsertAll(list)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPrice(key: String): String? = withContext(Dispatchers.IO) {
        db.priceDao().getValue(key)
    }

    suspend fun getAll(): List<PriceEntity> = withContext(Dispatchers.IO) {
        db.priceDao().getAll()
    }
}
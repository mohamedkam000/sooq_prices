package com.sooq.price.data

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private val httpClient = HttpClient(Android) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
}

val jsonParser = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

suspend fun fetchPriceData(context: Context, githubRawUrl: String): PriceData {
    val fileManager = FileStorageManager(context)

    val cachedJson = fileManager.loadJson()
    if (cachedJson != null) {
        try {
            return jsonParser.decodeFromString<PriceData>(cachedJson)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    val response = httpClient.get(githubRawUrl)
    val freshJson = response.bodyAsText()

    val priceData = jsonParser.decodeFromString<PriceData>(freshJson)
    
    fileManager.saveJson(freshJson)
    
    return priceData
}
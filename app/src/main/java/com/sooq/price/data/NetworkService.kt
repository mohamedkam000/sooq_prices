package com.sooq.price.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
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

suspend fun fetchPriceData(githubRawUrl: String): PriceData? {
    return try {
        httpClient.get(githubRawUrl).body<PriceData>()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
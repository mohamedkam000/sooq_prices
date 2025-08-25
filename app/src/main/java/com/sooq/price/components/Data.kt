package com.sooq.price.components

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class DataModel(
    val date: String,
    val tomatoes_b: String,
    val potatoes_k: String
)

fun loadJson(context: Context): DataModel {
    val jsonString = context.assets.open("data.json")
        .bufferedReader()
        .use { it.readText() }

    val json = Json { ignoreUnknownKeys = true }
    return json.decodeFromString<DataModel>(jsonString)
}
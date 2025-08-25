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

fun loadJson(context: Context): DataModel? {
    return try {
        val file = File(context.filesDir, "data.json")
        val jsonString = file.readText()
        val json = Json { ignoreUnknownKeys = true }
        json.decodeFromString<DataModel>(jsonString)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
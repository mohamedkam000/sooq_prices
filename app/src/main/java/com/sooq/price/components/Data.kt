package com.sooq.price.components



@Serializable
data class DataModel(
    val date: String,
    val tomatoes_b: String
    val potatoes_k: String
)

fun loadJson(context: Context): DataModel {
    val jsonString = context.assets.open("data.json")
        .bufferedReader()
        .use { it.readText() }

    return Json.decodeFromString<DataModel>(jsonString)
}


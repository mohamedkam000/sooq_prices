package com.sooq.price.data

import android.content.Context
import java.io.File

private const val PRICE_DATA_FILENAME = "price_data_cache.json"

class FileStorageManager(private val context: Context) {

    fun saveJson(jsonString: String) {
        try {
            val file = File(context.filesDir, PRICE_DATA_FILENAME)
            file.writeText(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadJson(): String? {
        return try {
            val file = File(context.filesDir, PRICE_DATA_FILENAME)
            if (file.exists()) {
                file.readText()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
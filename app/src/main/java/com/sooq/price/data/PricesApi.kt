package com.sooq.price.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.*
import kotlinx.serialization.json.*
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.*
import retrofit2.http.*
import okhttp3.*
import java.io.*

interface PricesApi {
    @GET("/mohamedkam000/prices/main/data.json")
    suspend fun fetchRaw(): Map<String, String>

    companion object {
        fun create(okHttpClient: OkHttpClient): PricesApi {
            val json = Json { ignoreUnknownKeys = true }
            val contentType = "application/json".toMediaType()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                .client(okHttpClient)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
            return retrofit.create(PricesApi::class.java)
        }
    }
}
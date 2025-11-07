package com.sooq.price.data

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.create
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Cache
import java.io.File

interface PricesApi {
    @GET("/mohamedkam000/prices/main/data.json")
    suspend fun fetchRaw(): Map<String, String>

    companion object {
        fun create(okHttpClient: OkHttpClient): PricesApi {
            val json = Json { ignoreUnknownKeys = true }
            val retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                .client(okHttpClient)
                .addConverterFactory(com.squareup.retrofit2.converter.kotlinx.serialization.asConverterFactory(
                    okhttp3.MediaType.get("application/json"), json))
                .build()
            return retrofit.create(PricesApi::class.java)
        }
    }
}
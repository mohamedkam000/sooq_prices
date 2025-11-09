package com.sooq.price.data.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/mohamedkam000/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val service: ApiService = retrofit.create(ApiService::class.java)
}
package com.sooq.price.data.remote

import com.sooq.price.data.model.Root
import retrofit2.http.GET

interface ApiService {
    @GET("sooq_prices/main/data.json")
    suspend fun fetchData(): Root
}
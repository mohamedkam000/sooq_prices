package com.sooq.price.data.network

import com.sooq.price.data.StatesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val client: HttpClient) {
    
    private val dataUrl = "https://raw.githubusercontent.com/mohamedkam000/sooq_prices/main/data.json"

    suspend fun getPriceData(): StatesResponse {
        return client.get(dataUrl).body()
    }
}
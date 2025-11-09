package com.sooq.price.data.repository

import com.sooq.price.data.local.ApplePriceDao
import com.sooq.price.data.model.ApplePriceEntity
import com.sooq.price.data.remote.ApiClient

class PriceRepository(private val dao: ApplePriceDao) {

    suspend fun refreshData() {
        val data = ApiClient.service.fetchData()

        val appleItems = data.states
            .flatMap { it.markets }
            .flatMap { it.goods }
            .filter { it.id == "fruits" }
            .flatMap { it.items }
            .filter { it.id == "apples" }

        val prices = appleItems.flatMap {
            it.prices?.map { (variety, price) ->
                ApplePriceEntity(variety, price)
            } ?: emptyList()
        }

        dao.insertAll(prices)
    }

    suspend fun getAllPrices(): List<ApplePriceEntity> = dao.getAll()
}
package com.sooq.price.data

import com.sooq.price.data.local.PriceDao
import com.sooq.price.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PriceRepository @Inject constructor(
    private val apiService: ApiService,
    private val priceDao: PriceDao
) {

    fun getItem(itemId: String): Flow<ItemEntity?> {
        return priceDao.getItemById(itemId)
    }
    
    suspend fun refreshData() {
        try {
            val response = apiService.getPriceData()
            val items = mutableListOf<ItemEntity>()

            response.states.forEach { state ->
                state.markets.forEach { market ->
                    market.goods.forEach { goods ->
                        goods.items.forEach { item ->
                            items.add(
                                ItemEntity(
                                    id = item.id,
                                    name = item.name,
                                    img = item.img,
                                    prices = item.prices ?: emptyMap(),
                                    notes = item.notes,
                                    goodsId = goods.id,
                                    marketId = market.id,
                                    stateId = state.id
                                )
                            )
                        }
                    }
                }
            }
            
            priceDao.clearAllItems()
            priceDao.insertItems(items)
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
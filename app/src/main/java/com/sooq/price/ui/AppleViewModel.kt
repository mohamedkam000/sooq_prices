package com.sooq.price.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.sooq.price.data.local.AppDatabase
import com.sooq.price.data.repository.PriceRepository
import com.sooq.price.data.model.ApplePriceEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppleViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "prices.db"
    ).build().applePriceDao()

    private val repo = PriceRepository(dao)

    private val _applePrices = MutableStateFlow<List<ApplePriceEntity>>(emptyList())
    val applePrices: StateFlow<List<ApplePriceEntity>> = _applePrices

    init {
        viewModelScope.launch {
            repo.refreshData()
            _applePrices.value = repo.getAllPrices()
        }
    }
}
package com.sooq.price

import android.app.Application
import com.sooq.price.data.PriceRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class PriceApplication : Application() {

    @Inject
    lateinit var repository: PriceRepository
    
    private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        triggerInitialUpdate()
    }
    
    private fun triggerInitialUpdate() {
        applicationScope.launch {
            repository.refreshData()
        }
    }
}
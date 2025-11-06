package com.sooq.price.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooq.price.data.PriceRepository
import com.sooq.price.data.PricesDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PriceState(
    val loading: Boolean = false,
    val data: PricesDto? = null,
    val error: String? = null
)

/*class PriceViewModel(
    private val repo: PriceRepository = PriceRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(PriceState(loading = true))
    val state: StateFlow<PriceState> = _state

    init {
        refresh()
    }

    fun refresh() {
        _state.value = PriceState(loading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val dto = repo.fetchPrices()
            _state.value = if (dto != null) PriceState(loading = false, data = dto)
            else PriceState(loading = false, error = "Failed to load prices")
        }
    }
}*/

class PriceViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "prices.db"
    ).build()

    private val repo = PriceRepository(dao = db.cacheDao())
    ...
}
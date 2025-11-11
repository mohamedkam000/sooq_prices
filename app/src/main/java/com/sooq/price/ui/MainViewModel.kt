package com.sooq.price.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sooq.price.data.CardNode
import com.sooq.price.data.PriceData
import com.sooq.price.data.fetchPriceData
import com.sooq.price.data.toCardNodeList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UiState {
    data object Loading : UiState
    data class Success(val data: List<CardNode>) : UiState
    data class Error(val message: String) : UiState
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val githubRawUrl = "https://raw.githubusercontent.com/mohamedkam000/sooq_prices/main/data.json"

    init {
        loadPriceData()
    }

    private fun loadPriceData() {
        viewModelScope.launch {
            try {
                val priceData = fetchPriceData(application, githubRawUrl)
                if (priceData != null) {
                    val cardList = priceData.toCardNodeList()
                    _uiState.value = UiState.Success(cardList)
                } else {
                    _uiState.value = UiState.Error("Failed to load data. Check network connection.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = UiState.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }
}
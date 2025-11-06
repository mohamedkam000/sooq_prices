package com.sooq.price.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sooq.price.data.MarketsByState
import com.sooq.price.ui.components.BlurredBackground
import com.sooq.price.ui.components.GlassCard

@Composable
fun MarketsScreen(state: String, onMarketClick: (String) -> Unit) {
    val markets = MarketsByState[state].orEmpty()
    Box(Modifier.fillMaxSize()) {
        BlurredBackground()
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "$state markets",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.height(12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(markets.size) { idx ->
                    val market = markets[idx]
                    GlassCard(title = market, subtitle = "Browse categories") {
                        onMarketClick(market)
                    }
                }
            }
        }
    }
}
package com.sooq.price.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sooq.price.model.*

data class MarketItem(
    val name: String,
    val price: Int
)

@Composable
fun MarketBubble(item: MarketItem) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Column {
            Text(item.name, style = MaterialTheme.typography.titleMedium)
            Text("${item.price} SDG", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
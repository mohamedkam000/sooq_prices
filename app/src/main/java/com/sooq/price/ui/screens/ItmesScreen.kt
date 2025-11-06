package com.sooq.price.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sooq.price.data.ItemsByCategory
import com.sooq.price.ui.components.BlurredBackground
import com.sooq.price.ui.components.GlassCard

@Composable
fun ItemsScreen(
    state: String,
    market: String,
    category: String,
    onItemClick: (String) -> Unit
) {
    val items = ItemsByCategory[category].orEmpty()
    Box(Modifier.fillMaxSize()) {
        BlurredBackground()
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "$category items",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.height(12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items.size) { idx ->
                    val item = items[idx]
                    GlassCard(title = item.name, subtitle = "View price") {
                        onItemClick(item.name)
                    }
                }
            }
        }
    }
}
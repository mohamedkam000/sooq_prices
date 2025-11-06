package com.sooq.price.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sooq.price.data.Categories
import com.sooq.price.ui.components.BlurredBackground
import com.sooq.price.ui.components.GlassCard

@Composable
fun CategoriesScreen(
    state: String,
    market: String,
    onCategoryClick: (String) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        BlurredBackground()
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "Categories in $market",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.height(12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(Categories.size) { idx ->
                    val c = Categories[idx]
                    GlassCard(title = c, subtitle = "Open items") {
                        onCategoryClick(c)
                    }
                }
            }
        }
    }
}
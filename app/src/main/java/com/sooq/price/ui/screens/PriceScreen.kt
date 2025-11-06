package com.sooq.price.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sooq.price.data.ItemsByCategory
import com.sooq.price.data.ItemPrice
import com.sooq.price.ui.PriceState
import com.sooq.price.ui.PriceViewModel
import com.sooq.price.ui.components.BlurredBackground

@Composable
fun PriceScreen(itemName: String, vm: PriceViewModel = viewModel()) {
    val state by vm.state.collectAsState()

    Box(Modifier.fillMaxSize()) {
        BlurredBackground()
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = itemName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.height(16.dp))

            when {
                state.loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                }
                state.error != null -> {
                    Text(
                        text = state.error ?: "Error",
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { vm.refresh() }) {
                        Text("Retry")
                    }
                }
                else -> {
                    val item: ItemPrice? = ItemsByCategory
                        .values
                        .flatten()
                        .find { it.name == itemName }

                    if (item == null) {
                        Text("Item mapping not found.")
                    } else {
                        val sKey = item.keySingle
                        val lKey = item.keyLarge
                        val single = state.data?.let { dto ->
                            dto::class.members.find { it.name == sKey }?.call(dto) as? String
                                ?: dtoField(dto, sKey)
                        }
                        val large = state.data?.let { dto ->
                            dto::class.members.find { it.name == lKey }?.call(dto) as? String
                                ?: dtoField(dto, lKey)
                        }

                        AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                            PriceCard(single = single, large = large)
                        }
                    }
                }
            }
        }
    }
}

private fun dtoField(dto: Any, key: String): String? {
    return null
}

@Composable
private fun PriceCard(single: String?, large: String?) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = "Price details",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.height(10.dp))
            Row {
                Text("Single:", color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.width(8.dp))
                Text(single ?: "—", color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(Modifier.height(8.dp))
            Row {
                Text("Large:", color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.width(8.dp))
                Text(large ?: "—", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
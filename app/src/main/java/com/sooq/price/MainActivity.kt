package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide action bar/title bar so the app looks like a single seamless screen
        try { supportActionBar?.hide() } catch (_: Exception) {}

        setContent {
            MaterialTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    // Full-screen background (you can replace with an image or gradient)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0F1724), // deep background — adjust as needed
                        Color(0xFF071127)
                    )
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        // Outer padding similar to your CSS .app padding + top margin
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, start = 20.dp, end = 20.dp, bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // The single shell container (rounded rectangle, max width)
            Shell {
                // Inside the shell: vertically stacked cards (one by one)
                val sample = sampleGoods()
                GoodsList(goods = sample)
            }
        }
    }
}

@Composable
fun Shell(content: @Composable ColumnScope.() -> Unit) {
    val maxWidthDp = 1200.dp

    // Subtle vertical glass-like gradient (no heavy blur)
    val shellBrush = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.03f),
            Color.White.copy(alpha = 0.01f)
        )
    )

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .widthIn(max = maxWidthDp)
            .shadow(elevation = 20.dp, shape = RoundedCornerShape(28.dp))
            .background(brush = shellBrush, shape = RoundedCornerShape(28.dp))
            .padding(20.dp)
    ) {
        content()
    }
}

@Composable
fun GoodsList(goods: List<GoodItem>) {
    // Vertical list of cards one after another
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(goods) { good ->
            MarketCard(name = good.name, subtitle = good.subtitle, price = good.price)
        }
    }
}

@Composable
fun MarketCard(name: String, subtitle: String, price: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 84.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        // inner gradient for card surface
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.015f),
                            Color.White.copy(alpha = 0.008f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp)
                    )
                }

                // Price tag on the right
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = price,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

/** Sample data model and sample items — replace with your real data source. */
data class GoodItem(val name: String, val subtitle: String, val price: String)

fun sampleGoods(): List<GoodItem> = listOf(
    GoodItem("Apples", "Multiple markets • Kg", "22,000"),
    GoodItem("Tomatoes", "Single market • Kg", "18,500"),
    GoodItem("Onions", "Multiple markets • Kg", "9,000"),
    GoodItem("Rice (local)", "Per sack", "120,000"),
    GoodItem("Sugar", "Per sack", "85,000"),
    GoodItem("Fresh Milk", "Per liter", "5,500")
)
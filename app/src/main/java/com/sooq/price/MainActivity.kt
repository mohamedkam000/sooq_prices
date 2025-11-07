package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DynamicThemeApp()
        }
    }
}

@Composable
fun DynamicThemeApp() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme())
            dynamicDarkColorScheme(LocalContext.current)
        else
            dynamicLightColorScheme(LocalContext.current)
    ) {
        ShellContent()
    }
}

@Composable
fun ShellContent() {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.87f)
                .clip(RoundedCornerShape(40.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            colors.surfaceVariant.copy(alpha = 0.15f),
                            colors.surfaceVariant.copy(alpha = 0.08f)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            GoodsGrid(sampleGoods())
        }
    }
}

@Composable
fun GoodsGrid(goods: List<GoodItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        items(goods) { good ->
            GoodCard(good)
        }
    }
}

@Composable
fun GoodCard(good: GoodItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = good.imageRes),
                contentDescription = good.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.75f)
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = good.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = good.price,
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}

data class GoodItem(
    val name: String,
    val price: String,
    val imageRes: Int
)

fun sampleGoods(): List<GoodItem> = listOf(
    GoodItem("Apples", "22,000 SDG", R.drawable.apples),
    GoodItem("Tomatoes", "18,500 SDG", R.drawable.apples),
    GoodItem("Onions", "9,000 SDG", R.drawable.apples),
    GoodItem("Rice (Local)", "120,000 SDG", R.drawable.apples),
    GoodItem("Sugar", "85,000 SDG", R.drawable.apples),
    GoodItem("Fresh Milk", "5,500 SDG", R.drawable.apples)
)
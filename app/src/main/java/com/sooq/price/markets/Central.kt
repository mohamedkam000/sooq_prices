package com.sooq.price.markets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.graphics.ColorUtils
import com.sooq.price.R
import com.sooq.price.ui.theme.*
import com.sooq.price.ui.getGreeting
import com.sooq.price.ui.components.*
import com.sooq.price.model.*

// Material 3
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*

// Navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Compose UI
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import androidx.compose.ui.res.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.*

// Compose foundation
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.layout.*

// Compose animation
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*

// Compose runtime
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cen(navController: NavHostController) {
    val context = LocalContext.current
var marketData by remember { mutableStateOf<MarketData?>(null) }

LaunchedEffect(Unit) {
    marketData = loadMarketData(context)
}

    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val greeting = remember { getGreeting() }

    val scrollState = rememberScrollState()
    val maxFontSize = 36.sp
    val minFontSize = 18.sp
    val maxTopPadding = 40.dp
    val minTopPadding = 0.dp
    val spacerHeight = 150.dp
    val collapseRangeDp = 200.dp
    val density = LocalDensity.current
    val spacerPx = with(density) { spacerHeight.toPx() }
    val collapseRangePx = with(density) { collapseRangeDp.toPx() }
    val collapseFraction = ((scrollState.value - spacerPx) / collapseRangePx).coerceIn(0f, 1f)
    val animatedFontSize = lerp(maxFontSize, minFontSize, collapseFraction)
    val animatedTopPadding = lerp(maxTopPadding, minTopPadding, collapseFraction)
    val titleAlpha by animateFloatAsState(targetValue = collapseFraction)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Placeholder",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(spacerHeight))

                    Text(
                        text = greeting,
                        fontSize = animatedFontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = animatedTopPadding)
                    )

                    Spacer(modifier = Modifier.height(100.dp))

                    marketData?.let { data ->
                        if (marketData == null) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        } else {
                            LazyColumn {
                                items(data.prices.entries.toList()) { entry ->
                                    MarketBubble(
                                        MarketItem(
                                            name = entry.key,
                                            price = entry.value.toIntOrNull() ?: 0
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
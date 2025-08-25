package com.sooq.price.categories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.graphics.ColorUtils
import com.sooq.price.R
import com.sooq.price.ui.*
import com.sooq.price.ui.theme.*
import com.sooq.price.components.*
import kotlinx.coroutines.*

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
import androidx.compose.foundation.layout.*

// Compose animation
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*

// Compose runtime
import androidx.compose.runtime.*

@Composable
fun Bubble(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(Color(0xFFBBDEFB), shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BubbleRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Bubble(text = label, modifier = Modifier.weight(1f))
        Bubble(text = value, modifier = Modifier.weight(1f))
    }
}

@Composable
fun BubbleScreen(data: DataModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        BubbleRow(label = "Tomatoe Box", value = data.tomatoes_b)
        BubbleRow(label = "Potato (KG)", value = data.potatoes_k)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Veg(navController: NavHostController) {
    val context = LocalContext.current
    val data = loadJson(context)

    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val greeting = stringResource(R.string.veg)

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
                        text = data?.date ?: "Data Error",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                actions = {
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            jsonDownloader.downloadJsonFile(context)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
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
                BubbleScreen(data = data)

            }
        }
    )
}
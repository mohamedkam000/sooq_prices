package com.sooq.price.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.graphics.ColorUtils
import com.sooq.price.R

// Material 3
import androidx.compose.material3.*

// Navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Compose UI
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.*
import androidx.compose.ui.res.stringResource

// Compose foundation
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

// Compose animation
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*

// Compose runtime
import androidx.compose.runtime.*
import com.sooq.price.ui.settings.*

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction
}

@Composable
fun MainScreen(navController: NavHostController) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val scrollState = rememberScrollState()

    val density = LocalDensity.current

    val spacer150DpPx = with(density) { 150.dp.toPx() }
    val spacer48DpPx = with(density) { 48.dp.toPx() }
    val cardHeightPx = with(density) { 150.dp.toPx() }
    val rowSpacingPx = spacer48DpPx

    // Calculate offsets for categories manually:
    // Category "Automotive" starts after:
    // Spacer(150.dp) + Text + Spacer(100.dp) + Padding (16.dp horizontal doesn't affect vertical)
    // Let’s assume "Hello There!" Text height + padding ~ 60dp vertical (rough estimate)
    val helloTextHeightPx = with(density) { 60.dp.toPx() }
    val spacer100DpPx = with(density) { 100.dp.toPx() }

    // Automotive section starts at:
    val automotiveStart = spacer150DpPx + helloTextHeightPx + spacer100DpPx

    // Automotive content height:
    // 1 row (2 cards): cardHeightPx
    // Spacer(48.dp)
    // 2nd row (cloth + footwear): cardHeightPx
    // Spacer(48.dp)
    val automotiveHeight = cardHeightPx + spacer48DpPx + cardHeightPx + spacer48DpPx

    // We'll create categories list with start and end scroll values:
    data class CategoryPosition(val title: String, val start: Float, val end: Float)

    val categories = listOf(
        CategoryPosition("Automotive", automotiveStart, automotiveStart + automotiveHeight),
        CategoryPosition("Clothing", automotiveStart + automotiveHeight, Float.MAX_VALUE)
        // Extend with more categories if needed
    )

    // Find the current category index based on scroll position
    val scrollY = scrollState.value.toFloat()
    val currentCategoryIndex = categories.indexOfLast { scrollY >= it.start }.coerceAtLeast(0)
    val nextCategoryIndex = (currentCategoryIndex + 1).coerceAtMost(categories.size - 1)

    val currentCategory = categories[currentCategoryIndex]
    val nextCategory = categories[nextCategoryIndex]

    val fadeProgress = ((scrollY - currentCategory.end) / (nextCategory.start - currentCategory.end))
        .coerceIn(0f, 1f)

    val currentAlpha = (1f - fadeProgress).coerceIn(0f, 1f)
    val nextAlpha = fadeProgress.coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        SettingsIconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            onClick = { }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            Text(
                text = "Hello There!",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 40.dp)
            )

            Spacer(modifier = Modifier.height(100.dp))

            // Category: Automotive
            Text(
                text = "Automotive",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    onClick = { navController.navigate("automotive") },
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.medium),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.car),
                        contentDescription = "Automotive Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Card(
                    onClick = { navController.navigate("beverage") },
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.medium),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.beverage),
                        contentDescription = "Beverage Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Category: Clothing
            Text(
                text = "Clothing",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    onClick = { navController.navigate("cloth") },
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.medium),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cloth),
                        contentDescription = "Cloth Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Card(
                    onClick = { navController.navigate("footwear") },
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.medium),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.footwear),
                        contentDescription = "Footwear Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    onClick = { navController.navigate("automotive") },
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.medium),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.car),
                        contentDescription = "Automotive Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Card(
                    onClick = { navController.navigate("beverage") },
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.medium),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.beverage),
                        contentDescription = "Beverage Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Category: Clothing
            Text(
                text = "Clothing",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    onClick = { navController.navigate("cloth") },
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.medium),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cloth),
                        contentDescription = "Cloth Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Card(
                    onClick = { navController.navigate("footwear") },
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.medium),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.footwear),
                        contentDescription = "Footwear Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // Sticky header with crossfade
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(backgroundColor)
                .padding(start = 16.dp)
                .padding(WindowInsets.statusBars.asPaddingValues()),
        ) {
            Text(
                text = currentCategory.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = currentAlpha),
                modifier = Modifier.align(Alignment.CenterStart)
            )
            if (currentCategoryIndex != nextCategoryIndex) {
                Text(
                    text = nextCategory.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = nextAlpha),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
        }
    }
}
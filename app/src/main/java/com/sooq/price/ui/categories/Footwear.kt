package com.sooq.price.ui.categories

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.*
import androidx.compose.ui.res.stringResource

// Compose foundation
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

// Compose animation
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween

// Compose runtime
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf

@Composable
fun Footwear(navController: NavHostController) {
    val backgroundColor = MaterialTheme.colorScheme.background

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
                        "Market",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background)
/*                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }*/
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
                        text = "Hello There!",
                        fontSize = animatedFontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = animatedTopPadding)
                    )

                    Spacer(modifier = Modifier.height(100.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Card(
//                                onClick = { navController.navigate("footwear") },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(150.dp)
                                    .clip(MaterialTheme.shapes.medium),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.footwear),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Card(
//                                onClick = { navController.navigate("footwear") },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(150.dp)
                                    .clip(MaterialTheme.shapes.medium),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.footwear),
                                    contentDescription = null,
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
//                                onClick = { navController.navigate("footwear") },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(150.dp)
                                    .clip(MaterialTheme.shapes.medium),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.footwear),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Card(
//                                onClick = { navController.navigate("footwear") },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(150.dp)
                                    .clip(MaterialTheme.shapes.medium),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.footwear),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        }
    )
}
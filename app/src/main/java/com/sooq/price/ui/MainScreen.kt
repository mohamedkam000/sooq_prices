package com.sooq.price.ui

import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.graphics.ColorUtils
import com.sooq.price.R
import com.sooq.price.ui.theme.*
import java.time.LocalTime
import java.net.URL
import java.io.File
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

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction
}

fun getGreeting(): String {
    val hour = LocalTime.now().hour
    return when (hour) {
        in 5..11 -> "Good Morning!"
        in 12..16 -> "Good Afternoon!"
        in 17..20 -> "Good Evening!"
        else -> "Good Night!"
    }
}

object jsonDownloader {
    suspend fun downloadJsonFile(context: Context) {
        val url = "https://raw.githubusercontent.com/mohamedkam000/prices/main/data.json"
    
        withContext(Dispatchers.IO) {
            try {
                val json = URL(url).readText()
                val file = File(context.filesDir, "data.json")
                file.writeText(json)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

@Composable
fun stringResource(id: Int): String {
    val context = LocalContext.current
    return remember(id) { context.getString(id) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val greeting = remember { getGreeting() }
    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant

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
                    IconButton(onClick = {
                        val context = LocalContext.current
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 8.dp)
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

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { navController.navigate("khr") },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.kh),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.kh),
                                    fontSize = 24.sp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { /*navController.navigate("jaz")*/ },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.jz),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.jz),
                                    fontSize = 24.sp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    
                        Spacer(modifier = Modifier.height(50.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { /*navController.navigate("khr")*/ },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.rn),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.rn),
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { /*navController.navigate("jaz")*/ },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.rs),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.rs),
                                    fontSize = 24.sp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(75.dp))
                
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { /*navController.navigate("khr")*/ },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ks),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.ks),
                                    fontSize = 20.sp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { /*navController.navigate("jaz")*/ },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.bn),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.bn),
                                    fontSize = 20.sp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(50.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { /*navController.navigate("khr")*/ },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.sn),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.sn),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { /*navController.navigate("jaz")*/ },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.blank),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(R.string.wn),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }

/*                        Spacer(modifier = Modifier.height(100.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { navController.navigate("khr") },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.khr),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Khartoum",
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { navController.navigate("jaz") },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.jaz),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Al-Jazeera",
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(100.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { navController.navigate("khr") },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.khr),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Khartoum",
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { navController.navigate("jaz") },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.jaz),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Al-Jazeera",
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(100.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { navController.navigate("khr") },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.khr),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Khartoum",
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        
                            Column(modifier = Modifier.weight(1f)) {
                                Card(
                                    onClick = { navController.navigate("jaz") },
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.jaz),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Al-Jazeera",
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }*/

                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    )
}
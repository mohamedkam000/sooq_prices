package com.sooq.price

import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.edit
import com.sooq.price.ui.*
import com.sooq.price.categories.*
import com.sooq.price.states.*
import com.sooq.price.markets.*
import com.sooq.price.ui.theme.*
import com.sooq.price.components.*

// Compose Foundation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*

// Material
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*

import androidx.compose.runtime.*

// Compose UI
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

//import coil.compose.AsyncImage
import kotlinx.coroutines.delay

import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.content.Context
import androidx.compose.ui.platform.LocalContext

// Compose Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Compose Animation
import androidx.compose.animation.core.*
import androidx.compose.animation.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            val context = LocalContext.current
            var data by remember { mutableStateOf<DataModel?>(null) }
            var isLoading by remember { mutableStateOf(true) }
            var hasError by remember { mutableStateOf(false) }
        
            LaunchedEffect(Unit) {
                try {
                    data = jsonDownloader.downloadAndLoadJson(context)
                    if (data == null) {
                        hasError = true
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Failed to load data", e)
                    hasError = true
                } finally {
                    isLoading = false
                }
            }
        
            when {
                isLoading -> LoadingScreen()
                hasError -> ErrorScreen(onRetry = {
                    isLoading = true
                    hasError = false
                })
                else -> {
                    SooqTheme {
                        AppNavigation(data = data!!)
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorScreen(onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onRetry() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "😞",
            fontSize = 64.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun AppNavigation(data: DataModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "main",
    ) {
        composable("main") { MainScreen(navController) }
        composable("kh") { Kh(navController) }
        composable("veg") { Veg(navController, data) }
        composable("central") { Cen(navController) }
    }
}
package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.sooq.price.ui.theme.SooqTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SooqTheme(dynamicColor = true) {
                SooqApp()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SooqApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.States) }
    var selectedState by remember { mutableStateOf<State?>(null) }
    var selectedMarket by remember { mutableStateOf<Market?>(null) }
    var selectedGood by remember { mutableStateOf<Good?>(null) }
    var selectedItem by remember { mutableStateOf<Item?>(null) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background),
        color = MaterialTheme.colorScheme.background
    ) {
        when (currentScreen) {
            is Screen.States -> StateList(
                onClick = {
                    selectedState = it
                    currentScreen = Screen.Markets
                }
            )
            is Screen.Markets -> selectedState?.let { state ->
                MarketList(state, onBack = { currentScreen = Screen.States }) {
                    selectedMarket = it
                    currentScreen = Screen.Goods
                }
            }
            is Screen.Goods -> selectedMarket?.let { market ->
                GoodsList(
                    market,
                    onBack = { currentScreen = Screen.Markets },
                    onClick = {
                        selectedGood = it
                        currentScreen = Screen.Items
                    }
                )
            }
            is Screen.Items -> selectedGood?.let { good ->
                ItemList(
                    good,
                    onBack = { currentScreen = Screen.Goods },
                    onClick = {
                        selectedItem = it
                        currentScreen = Screen.ItemDetail
                    }
                )
            }
            is Screen.ItemDetail -> selectedItem?.let { item ->
                ItemDetail(item, onBack = { currentScreen = Screen.Items })
            }
        }
    }
}

sealed class Screen {
    data object States : Screen()
    data object Markets : Screen()
    data object Goods : Screen()
    data object Items : Screen()
    data object ItemDetail : Screen()
}
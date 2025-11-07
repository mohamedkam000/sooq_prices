package com.sooq.price.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

private val STATES = listOf("Khartoum", "Red Sea", "River Nile")
private val MARKETS = mapOf(
    "Khartoum" to listOf("Central Market", "Omdurman Market"),
    "Red Sea" to listOf("Port Market"),
    "River Nile" to listOf("River Market")
)

private val CATEGORIES = listOf("Fruits", "Vegetables", "Beverages", "Meat")

private val ITEMS = mapOf(
    "Fruits" to listOf("apples", "oranges", "grapes"),
    "Vegetables" to listOf("tomatoes", "potatoes"),
    "Beverages" to listOf("cola", "juice"),
    "Meat" to listOf("beef", "chicken")
)

@Composable
fun StatesScreen(navController: NavHostController) {
    Scaffold { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(STATES) { state ->
                Card(modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(160.dp)
                    .clickable { navController.navigate("markets/$state") }) {
                    Box(Modifier.fillMaxSize()) {
                        Text(text = state, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun MarketsScreen(navController: NavHostController, state: String) {
    val markets = MARKETS[state] ?: emptyList()
    Scaffold(topBar = { TopAppBar(title = { Text(state) }) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(markets) { mkt ->
                ListItem(headlineText = { Text(mkt) }, modifier = Modifier.clickable { navController.navigate("categories/$state/$mkt") })
                Divider()
            }
        }
    }
}

@Composable
fun CategoriesScreen(navController: NavHostController, state: String, market: String) {
    Scaffold(topBar = { TopAppBar(title = { Text(market) }) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(CATEGORIES) { cat ->
                Card(modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .clickable { navController.navigate("items/$state/$market/$cat") }) {
                    Text(text = cat, modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Composable
fun ItemsScreen(navController: NavHostController, repository: Repository, state: String, market: String, category: String) {
    val items = ITEMS[category] ?: emptyList()
    Scaffold(topBar = { TopAppBar(title = { Text(category) }) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items) { item ->
                ListItem(
                    headlineText = { Text(item.capitalize()) },
                    supportingText = { Text("Tap to see prices") },
                    modifier = Modifier.clickable { navController.navigate("detail/$item") }
                )
                Divider()
            }
        }
    }
}

@Composable
fun DetailScreen(navController: NavHostController, repository: Repository, itemKey: String) {
    var smallPrice by remember { mutableStateOf<String?>(null) }
    var bigPrice by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(itemKey) {
        val s = repository.getPrice("${itemKey}_s")
        val m = repository.getPrice("${itemKey}_m")
        smallPrice = s
        bigPrice = m
        val res = repository.refreshAndCache()
        if (res.isSuccess) {
            smallPrice = repository.getPrice("${itemKey}_s")
            bigPrice = repository.getPrice("${itemKey}_m")
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text(itemKey.capitalize()) }) }) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {
            Text("Single item: ${smallPrice ?: "—"}", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))
            Text("Large pack: ${bigPrice ?: "—"}", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(20.dp))
            Button(onClick = { navController.popBackStack() }) { Text("Back") }
        }
    }
}
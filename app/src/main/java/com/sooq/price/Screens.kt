package com.sooq.price

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import com.sooq.price.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StateList(onClick: (State) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(sampleStates) { state ->
            SooqCard(
                title = state.name,
                subtitle = "Tap to explore markets",
                imageUrl = state.img,
                tag = "State",
                onClick = { onClick(state) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MarketList(state: State, onBack: () -> Unit, onClick: (Market) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = "${state.name} Markets", onBack = onBack)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(state.markets) { market ->
                SooqCard(
                    title = market.name,
                    subtitle = "Tap to view goods",
                    imageUrl = market.img,
                    tag = "Market",
                    onClick = { onClick(market) }
                )
            }
        }
    }
}

@Composable
fun GoodsList(market: Market, onBack: () -> Unit, onClick: (Good) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = "${market.name} Goods", onBack = onBack)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(market.goods) { good ->
                SooqCard(
                    title = good.name,
                    subtitle = "${good.items.size} items",
                    imageUrl = good.img,
                    tag = "Good",
                    onClick = { onClick(good) }
                )
            }
        }
    }
}

@Composable
fun ItemList(good: Good, onBack: () -> Unit, onClick: (Item) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = good.name, onBack = onBack)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(good.items) { item ->
                SooqCard(
                    title = item.name,
                    subtitle = "Price: ${item.price ?: "N/A"}",
                    imageUrl = item.img,
                    tag = "Item",
                    onClick = { onClick(item) }
                )
            }
        }
    }
}

@Composable
fun ItemDetail(item: Item, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopBar(title = item.name, onBack = onBack)
        Text(text = "Price: ${item.price ?: "N/A"}", style = MaterialTheme.typography.headlineSmall)
    }
}
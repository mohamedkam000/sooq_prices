package com.sooq.price.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sooq.price.data.ItemEntity
import com.sooq.price.ui.theme.AppMaterialTheme

@Composable
fun DetailScreen(
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel(),
    contentPadding: PaddingValues
) {
    val item by viewModel.item.collectAsState()

    item?.let {
        CollapsingDetailContent(navController = navController, item = it)
    }
}

@Composable
fun CollapsingDetailContent(
    navController: NavHostController,
    item: ItemEntity
) {
    val minHeaderHeight = 64.dp
    val maxHeaderHeight = 300.dp

    val minHeaderHeightPx = with(LocalDensity.current) { minHeaderHeight.toPx() }
    val maxHeaderHeightPx = with(LocalDensity.current) { maxHeaderHeight.toPx() }
    val scrollableHeightPx = maxHeaderHeightPx - minHeaderHeightPx

    val headerOffsetPx = remember { mutableFloatStateOf(0f) }

    val progress = remember {
        derivedStateOf {
            ((-headerOffsetPx.floatValue) / scrollableHeightPx).coerceIn(0f, 1f)
        }
    }

    val currentHeaderHeight = lerp(maxHeaderHeight, minHeaderHeight, progress.value)

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = headerOffsetPx.floatValue + delta
                val oldOffset = headerOffsetPx.floatValue
                headerOffsetPx.floatValue = newOffset.coerceIn(-scrollableHeightPx, 0f)
                val consumed = headerOffsetPx.floatValue - oldOffset
                return Offset(0f, consumed)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = currentHeaderHeight)
        ) {
            item {
                DetailContent(item = item)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(currentHeaderHeight)
                .align(Alignment.TopCenter),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shadowElevation = 4.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CollapsedHeader(progress = progress.value, item = item)
                ExpandedHeader(progress = progress.value, item = item)
            }
        }
    }
}

@Composable
private fun CollapsedHeader(progress: Float, item: ItemEntity) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .graphicsLayer { alpha = progress },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.img)
                    .crossfade(true)
                    .build(),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = item.name,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ExpandedHeader(progress: Float, item: ItemEntity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .graphicsLayer { alpha = 1f - progress },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.img)
                    .crossfade(true)
                    .build(),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = item.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DetailContent(item: ItemEntity) {
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        ItemPriceDetails(prices = item.prices, notes = item.notes)
    }
}

@Composable
private fun ItemPriceDetails(prices: Map<String, Double>, notes: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Pricing",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (prices.isNotEmpty()) {
            prices.forEach { (unit, price) ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    tonalElevation = 2.dp
                ) {
                    PriceRow(
                        name = unit,
                        price = price,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } else {
            Text(
                text = "No pricing information available.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        notes?.let {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Notes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PriceRow(name: String, price: Double, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "SDG ${"%.2f".format(price)}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


/*@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val previewItem = ItemEntity(
        id = "apples",
        name = "Apples",
        img = "https://example.com/apple.jpg",
        prices = mapOf(
            "Piece" to 1000.00,
            "Basket (20)" to 18000.00
        ),
        notes = "Fresh red apples.",
        goodsId = "fruits",
        marketId = "central",
        stateId = "khartoum"
    )
    
    AppMaterialTheme {
        CollapsingDetailContent(
            navController = rememberNavController(),
            item = previewItem
        )
    }
}*/
package com.sooq.price.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sooq.price.ui.theme.AppMaterialTheme
import com.sooq.price.data.CardNode
import com.sooq.price.data.cardTreeData
import kotlin.math.roundToInt

@Composable
fun DetailScreen(
    navController: NavHostController,
    card: CardNode,
    contentPadding: PaddingValues
) {
    val maxHeaderHeight = 350.dp
    val minHeaderHeight = 120.dp
    val maxImageSize = 300.dp
    val minImageSize = 80.dp

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val maxHeaderHeightPx = with(LocalDensity.current) { maxHeaderHeight.toPx() }
    val minHeaderHeightPx = with(LocalDensity.current) { minHeaderHeight.toPx() }
    val headerOffsetPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = headerOffsetPx.floatValue + delta
                val oldOffset = headerOffsetPx.floatValue
                headerOffsetPx.floatValue = newOffset.coerceIn(-(maxHeaderHeightPx - minHeaderHeightPx), 0f)
                val consumed = headerOffsetPx.floatValue - oldOffset
                return Offset(0f, consumed)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = contentPadding.calculateBottomPadding())
            .nestedScroll(nestedScrollConnection)
    ) {
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = maxHeaderHeight)
        ) {
            item {
                DetailContent(card = card)
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeaderHeight)
                .graphicsLayer { translationY = headerOffsetPx.floatValue }
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
        ) {
            val progress = ((-headerOffsetPx.floatValue / (maxHeaderHeightPx - minHeaderHeightPx)))
                .coerceIn(0f, 1f)

            val imageSize = lerp(maxImageSize, minImageSize, progress)
            val imageYOffset = lerp(
                (maxHeaderHeight - maxImageSize) / 2,
                (minHeaderHeight - minImageSize) / 2,
                progress
            )
            val imageXOffset = lerp(
                (screenWidth - maxImageSize) / 2,
                16.dp,
                progress
            )

            Box(
                modifier = Modifier
                    .size(imageSize)
                    .offset { IntOffset(imageXOffset.toPx().roundToInt(), imageYOffset.toPx().roundToInt()) }
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(card.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = card.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .background(
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun DetailContent(card: CardNode) {
    Column {
        Text(
            text = card.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))
        
        when (card.title) {
            "Apples" -> AppleSpecificDetails()
            
            else -> DefaultDetailContent(card = card)
        }
    }
}

@Composable
private fun AppleSpecificDetails() {
    val appleVarieties = mapOf(
        "Red Delicious" to 800.00,
        "Granny Smith" to 750.00,
        "Gala" to 780.00,
        "Fuji" to 820.00
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Variety Pricing (per Kg)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        appleVarieties.forEach { (name, price) ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 2.dp
            ) {
                PriceRow(
                    name = name,
                    price = price,
                    modifier = Modifier.padding(16.dp)
                )
            }
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

@Composable
private fun DefaultDetailContent(card: CardNode) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        card.price?.let { price ->
            val formattedPrice = "SDG ${"%.2f".format(price)}"
            Text(
                text = formattedPrice,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        Text(
            text = card.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    AppMaterialTheme {
        DetailScreen(
            navController = rememberNavController(),
            card = cardTreeData[0].children[0].children[0].children[0],
            contentPadding = PaddingValues(0.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenDefaultPreview() {
    AppMaterialTheme {
        DetailScreen(
            navController = rememberNavController(),
            card = cardTreeData[0].children[0].children[2].children[0],
            contentPadding = PaddingValues(0.dp)
        )
    }
}
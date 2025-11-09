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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sooq.price.AppMaterialTheme
import com.sooq.price.data.CardNode
import com.sooq.price.data.cardTreeData

@Composable
fun DetailScreen(
    navController: NavHostController,
    card: CardNode,
    contentPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .verticalScroll(rememberScrollState())
    ) {
        
        Box(modifier = Modifier.fillMaxWidth()) {
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
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

        Spacer(modifier = Modifier.height(24.dp))

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

        DetailContent(card = card)
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun DetailContent(card: CardNode) {
    when (card.title) {
        "Apples" -> AppleSpecificDetails()
        
        else -> DefaultDetailContent(card = card)
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Variety Pricing (per Kg)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                appleVarieties.forEach { (name, price) ->
                    PriceRow(name = name, price = price)
                }
            }
        }
    }
}

@Composable
private fun PriceRow(name: String, price: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
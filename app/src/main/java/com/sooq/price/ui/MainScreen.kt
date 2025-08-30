package com.sooq.price.ui

// Compose basics
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape

// Material3
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults

// Compose runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// Compose UI
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Compose Navigation
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// Coil for image loading
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val markets = listOf(
        Market(
            "Central Market",
            "central",
            "https://www.sudanakhbar.com/wp-content/uploads/2022/11/%D8%B5%D9%8A%D9%86%D9%8A%D8%A9-%D8%A7%D9%84%D8%B3%D9%88%D9%82-%D8%A7%D9%84%D9%85%D8%B1%D9%83%D8%B2%D9%8A-%D8%A7%D9%84%D8%AE%D8%B1%D8%B7%D9%88%D9%85.jpg"
        ),
        Market(
            "Local Market",
            "local",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRs02t35M8vuXehiN0HdQ_AyBlrrUQlxzNXWQ&s"
        ),
        Market(
            "Al-Arabi Market",
            "arabi",
            "https://mujaz.alahdath.news/wp-content/uploads/2025/07/%D8%A7%D9%84%D8%B3%D9%88%D9%82-%D8%A7%D9%84%D8%B9%D8%B1%D8%A8%D9%8A-_-%D8%A7%D9%84%D8%AE%D8%B1%D8%B7%D9%88%D9%85--scaled.jpg"
        ),
        Market(
            "Sabreen Market",
            "sabreen",
            "https://alnawrs.com/wp-content/uploads/2025/06/%D8%B3%D9%88%D9%82-%D8%B5%D8%A7%D8%A8%D8%B1%D9%8A%D9%86.png"
        )
    )

    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background
    val contentColor = if (isDarkTheme) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onBackground

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Logo placeholder
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Sooq Price",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = contentColor
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = padding,
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(markets) { market ->
                    MarketCard(market) {
                        navController.navigate(market.route)
                    }
                }
            }
        }
    )
}

@Composable
fun MarketCard(market: Market, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box {
            // Image
            Image(
                painter = rememberAsyncImagePainter(market.imageUrl),
                contentDescription = market.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Name overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                    .padding(8.dp)
            ) {
                Text(
                    market.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

data class Market(
    val name: String,
    val route: String,
    val imageUrl: String
)

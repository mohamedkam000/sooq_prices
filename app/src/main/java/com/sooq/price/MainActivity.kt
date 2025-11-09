package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sooq.price.data.CardNode
import com.sooq.price.data.getListFromPath
import com.sooq.price.data.getNodeFromPath
import com.sooq.price.ui.DetailScreen
import com.sooq.price.ui.theme.AppMaterialTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
//        enableEdgeToEdge()

        setContent {
            AppMaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AppShell()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .width(360.dp)
            .height(780.dp)
            .clip(RoundedCornerShape(48.dp))
            .shadow(20.dp, shape = RoundedCornerShape(48.dp)),
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "list/",
            modifier = Modifier.fillMaxSize()
        ) {
            // Main + nested list screens
            composable("list/{path}") { backStackEntry ->
                val path = backStackEntry.arguments?.getString("path") ?: ""
                val cards = getListFromPath(path)
                val headerInfo = remember(path) {
                    // Main screen: title only. Nested list: image + title of section node.
                    if (path.isEmpty()) HeaderInfo(
                        title = "Sooq Price",
                        imageUrl = null
                    ) else {
                        val node = getNodeFromPath(path)
                        HeaderInfo(
                            title = node.title,
                            imageUrl = node.imageUrl
                        )
                    }
                }

                // Local list state to drive the collapse animation
                val listState = rememberLazyListState()
                val collapseFraction by remember {
                    derivedStateOf {
                        // Collapses based on top padding space being consumed by scroll
                        // We use the vertical scroll offset of the first visible item.
                        val offset = listState.firstVisibleItemScrollOffset.toFloat()
                        val maxCollapsePx = 140f // corresponds to ~140.dp of collapse range
                        (offset / maxCollapsePx).coerceIn(0f, 1f)
                    }
                }
                val animatedFraction by animateFloatAsState(targetValue = collapseFraction, label = "headerCollapse")

                Box(modifier = Modifier.fillMaxSize()) {
                    CollapsingHeader(
                        title = headerInfo.title,
                        imageUrl = headerInfo.imageUrl,
                        collapseFraction = animatedFraction,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(horizontal = 32.dp)
                            .padding(top = innerPadding.calculateTopPadding())
                    )

                    CardList(
                        navController = navController,
                        cards = cards,
                        currentPath = path,
                        contentPadding = innerPadding,
                        listState = listState
                    )
                }
            }

            // Detail screen
            composable("detail/{path}") { backStackEntry ->
                val path = backStackEntry.arguments?.getString("path")!!
                val card = getNodeFromPath(path)

                // For detail, we also show image + title; collapse is static unless DetailScreen provides scrolling.
                Box(modifier = Modifier.fillMaxSize()) {
                    CollapsingHeader(
                        title = card.title,
                        imageUrl = card.imageUrl,
                        collapseFraction = 0f, // stays expanded; you can wire this to a scroll state inside DetailScreen if needed
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(horizontal = 32.dp)
                            .padding(top = innerPadding.calculateTopPadding())
                    )

                    DetailScreen(
                        navController = navController,
                        card = card,
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyElevatedCard(title: String, imageUrl: String, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(36.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun CardList(
    navController: NavHostController,
    cards: List<CardNode>,
    currentPath: String,
    contentPadding: PaddingValues,
    listState: androidx.compose.foundation.lazy.LazyListState
) {
    // Header height ranges 72.dp..212.dp; we reserve top padding accordingly.
    val expandedHeaderHeight = 212.dp
    val bottomSpace = 50.dp

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding() + expandedHeaderHeight,
            bottom = contentPadding.calculateBottomPadding() + bottomSpace,
            start = 0.dp,
            end = 0.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(cards) { index, card ->
            MyElevatedCard(
                title = card.title,
                imageUrl = card.imageUrl,
                onClick = {
                    val newPath = if (currentPath.isEmpty()) "$index" else "${currentPath}_$index"
                    if (card.children.isEmpty()) {
                        navController.navigate("detail/$newPath")
                    } else {
                        navController.navigate("list/$newPath")
                    }
                }
            )
        }
    }
}

@Composable
fun CollapsingHeader(
    title: String,
    imageUrl: String?,
    collapseFraction: Float,
    modifier: Modifier = Modifier
) {
    // Height interpolates between expanded and collapsed
    val expandedHeight = 212.dp
    val collapsedHeight = 72.dp
    val heightPxRange = with(androidx.compose.ui.platform.LocalDensity.current) {
        (expandedHeight.toPx() - collapsedHeight.toPx())
    }
    val currentHeightPx = with(androidx.compose.ui.platform.LocalDensity.current) {
        collapsedHeight.toPx() + (1f - collapseFraction) * heightPxRange
    }
    val currentHeight = with(androidx.compose.ui.platform.LocalDensity.current) {
        currentHeightPx.toDp()
    }

    // Content interpolation factors
    val imageAlpha by animateFloatAsState(targetValue = if (imageUrl != null) (1f - collapseFraction) else 0f, label = "imageAlpha")
    val titleSizeCollapsed = MaterialTheme.typography.titleMedium
    val titleSizeExpanded = MaterialTheme.typography.headlineMedium
    val useExpandedStyle = (collapseFraction < 0.5f)
    val titleStyle = if (useExpandedStyle) titleSizeExpanded else titleSizeCollapsed

    ElevatedCard(
        modifier = modifier
            .requiredHeight(currentHeight),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.BottomStart
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color.Black.copy(alpha = 0.05f))
                        .then(Modifier)
                        .padding(0.dp),
                    alpha = imageAlpha
                )
                // Subtle overlay for text legibility when image is visible
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.15f * imageAlpha))
                )
            }

            Text(
                text = title,
                style = titleStyle,
                color = if (imageUrl != null) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 18.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppShellPreview() {
    AppMaterialTheme {
        AppShell()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkAppShellPreview() {
    AppMaterialTheme(darkTheme = true) {
        AppShell()
    }
}

@Preview
@Composable
fun MyElevatedCardPreview() {
    AppMaterialTheme {
        MyElevatedCard(
            title = "Preview Title",
            imageUrl = "https://picsum.photos/600/400?random=0",
            onClick = {}
        )
    }
}

/**
 * Simple holder for header content.
 */
data class HeaderInfo(
    val title: String,
    val imageUrl: String?
)
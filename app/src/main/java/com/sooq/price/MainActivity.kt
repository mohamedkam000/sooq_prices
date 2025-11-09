package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
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
            composable("list/{path}") { backStackEntry ->
                val path = backStackEntry.arguments?.getString("path") ?: ""
                val cards = getListFromPath(path)
                val headerInfo = remember(path) {
                    if (path.isEmpty()) HeaderInfo(
                        title = "Sooq Price"
                    ) else {
                        val node = getNodeFromPath(path)
                        HeaderInfo(
                            title = node.title
                        )
                    }
                }

                val listState = rememberLazyListState()
                val collapseFraction by remember {
                    derivedStateOf {
                        val offset = listState.firstVisibleItemScrollOffset.toFloat()
                        val maxCollapsePx = 140f
                        (offset / maxCollapsePx).coerceIn(0f, 1f)
                    }
                }
                val animatedFraction by animateFloatAsState(targetValue = collapseFraction, label = "headerCollapse")

                Box(modifier = Modifier.fillMaxSize()) {
                    CardList(
                        navController = navController,
                        cards = cards,
                        currentPath = path,
                        contentPadding = innerPadding,
                        listState = listState
                    )

                    CollapsingHeader(
                        title = headerInfo.title,
                        collapseFraction = animatedFraction,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(horizontal = 32.dp)
//                            .padding(top = innerPadding.calculateTopPadding())
                            .zIndex(1f)
                    )
                }
            }

            composable("detail/{path}") { backStackEntry ->
                val path = backStackEntry.arguments?.getString("path")!!
                val card = getNodeFromPath(path)

                Box(modifier = Modifier.fillMaxSize()) {
                    CollapsingHeader(
                        title = card.title,
                        collapseFraction = 0f,
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
    collapseFraction: Float,
    modifier: Modifier = Modifier
) {
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

    val titleSizeCollapsed = MaterialTheme.typography.titleMedium
    val titleSizeExpanded = MaterialTheme.typography.headlineMedium
    val useExpandedStyle = (collapseFraction < 0.5f)
    val titleStyle = if (useExpandedStyle) titleSizeExpanded else titleSizeCollapsed

    ElevatedCard(
        modifier = modifier.requiredHeight(currentHeight),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = title,
                style = titleStyle,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 18.dp)
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

/*@Preview
@Composable
fun MyElevatedCardPreview() {
    AppMaterialTheme {
        MyElevatedCard(
            title = "Preview Title",
            imageUrl = "https://picsum.photos/600/400?random=0",
            onClick = {}
        )
    }
}*/

data class HeaderInfo(
    val title: String,
)
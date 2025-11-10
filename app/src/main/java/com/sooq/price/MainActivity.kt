package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import dagger.hilt.android.*
import androidx.hilt.navigation.compose.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
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

@AndroidEntryPoint
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

                val scrollOffset by remember {
                    derivedStateOf {
                        listState.firstVisibleItemIndex * 300 + listState.firstVisibleItemScrollOffset
                    }
                }
                
                val collapseFractionTarget = (scrollOffset / 140f).coerceIn(0f, 1f)
                val animatedFraction by animateFloatAsState(
                    targetValue = collapseFractionTarget,
                    animationSpec = androidx.compose.animation.core.spring(
                        dampingRatio = 0.9f,
                        stiffness = 200f
                    ),
                    label = "headerCollapseSmooth"
                )

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
                        collapseFraction = 1f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
//                            .zIndex(1f)
                    )

                    DetailScreen(
                        navController = navController,
//                        viewModel = hiltViewModel(),
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
        Column(
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

            Spacer(modifier = Modifier.height(16.dp))

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
    listState: LazyListState
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
    val heightPxRange = with(LocalDensity.current) {
        (expandedHeight.toPx() - collapsedHeight.toPx())
    }
    val currentHeightPx = with(LocalDensity.current) {
        collapsedHeight.toPx() + (1f - collapseFraction) * heightPxRange
    }
    val currentHeight = with(LocalDensity.current) {
        currentHeightPx.toDp()
    }

    val shadowElevation = 6.dp * collapseFraction
    val backgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val titleStyle = if (collapseFraction < 0.5f) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.titleLarge

    Surface(
        modifier = modifier
            .requiredHeight(currentHeight)
            .shadow(shadowElevation),
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentAlignment = if (collapseFraction < 0.5f) Alignment.BottomStart else Alignment.CenterStart
        ) {
            Text(
                text = title,
                style = titleStyle,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
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

data class HeaderInfo(
    val title: String,
)
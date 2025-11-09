package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.style.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
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
                    color = MaterialTheme.colorScheme.background
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

    Box(
        modifier = Modifier
            .width(360.dp)
            .height(780.dp)
            .clip(RoundedCornerShape(48.dp))
            .shadow(20.dp, shape = RoundedCornerShape(48.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        NavHost(
            navController = navController,
            startDestination = "list/",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("list/{path}") { backStackEntry ->
                val path = backStackEntry.arguments?.getString("path") ?: ""
                val cards = getListFromPath(path)
                CardList(
                    navController = navController,
                    cards = cards,
                    currentPath = path
                )
            }
            
            composable("detail/{path}") { backStackEntry ->
                val path = backStackEntry.arguments?.getString("path")!!
                val card = getNodeFromPath(path)
                DetailScreen(
                    navController = navController,
                    card = card
                )
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
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardList(
    navController: NavHostController,
    cards: List<CardNode>,
    currentPath: String
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )
    
    // Determine the header title
    val currentTitle = if (currentPath.isEmpty()) "Categories" else getNodeFromPath(currentPath).title

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { 
                    if (currentPath.isEmpty()) {
                        // Main screen: simple large title that shrinks to a small title
                        Text(
                            text = currentTitle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        // Sub-categories: title + image header similar to details screen
                        CollapsingListHeader(
                            card = getNodeFromPath(currentPath),
                            scrollBehavior = scrollBehavior
                        )
                    }
                },
                navigationIcon = {
                    if (currentPath.isNotEmpty()) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp, // Add a bit of space below the header
                bottom = innerPadding.calculateBottomPadding() + 50.dp,
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
}

// A custom Composable for the sub-category collapsing header (title + image)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingListHeader(card: CardNode, scrollBehavior: TopAppBarScrollBehavior) {
    val progress = scrollBehavior.state.collapsedFraction
    
    // Scale for the large title (fades out as it collapses)
    val largeTitleAlpha = lerp(0.0f, 1.0f, 1f - progress).coerceIn(0f, 1f)
    
    // Scale for the image (shrinks as it collapses)
    val imageSize = lerp(50.dp, 100.dp, 1f - progress)
    
    val smallTitleAlpha = lerp(0.0f, 1.0f, progress)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image (Visible in expanded state, shrinks but stays in the row)
        Box(
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .graphicsLayer { alpha = largeTitleAlpha } // Fades out with the large title
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
        
        Spacer(modifier = Modifier.width(16.dp))

        // Title
        Column {
            // Large Title (Visible only when expanded)
            Text(
                text = card.title,
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.graphicsLayer { alpha = largeTitleAlpha }
            )
            
            // Small Title (Visible only when collapsed/scrolled)
            Text(
                text = card.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.graphicsLayer { alpha = smallTitleAlpha }
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
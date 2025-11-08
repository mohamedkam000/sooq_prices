package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.input.nestedscroll.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.style.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import java.text.*
import java.util.*
import coil.compose.*
import coil.request.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class CardNode(
    val title: String,
    val imageUrl: String,
    val children: List<CardNode> = emptyList()
)

private val cardTreeData = listOf(
    CardNode("Level 1 - Item 1", "https://picsum.photos/600/400?random=1", children = listOf(
        CardNode("Level 2 - Item 1.1", "https://picsum.photos/600/400?random=11", children = listOf(
            CardNode("Level 3 - Item 1.1.1", "https://picsum.photos/600/400?random=111", children = listOf(
                CardNode("Level 4 - Final Item 1", "https://picsum.photos/600/400?random=1111"),
                CardNode("Level 4 - Final Item 2", "https://picsum.photos/600/400?random=1112")
            ))
        )),
        CardNode("Level 2 - Item 1.2", "https://picsum.photos/600/400?random=12", children = listOf(
             CardNode("Level 3 - Item 1.2.1", "https://picsum.photos/600/400?random=121", children = listOf(
                CardNode("Level 4 - Final Item 3", "https://picsum.photos/600/400?random=1211")
            ))
        ))
    )),
    CardNode("Level 1 - Item 2", "https://picsum.photos/600/400?random=2", children = listOf(
        CardNode("Level 2 - Item 2.1", "https://picsum.photos/600/400?random=21", children = listOf(
            CardNode("Level 3 - Item 2.1.1", "https://picsum.photos/600/400?random=211", children = listOf(
                CardNode("Level 4 - Final Item 4", "https://picsum.photos/600/400?random=2111")
            ))
        ))
    )),
    CardNode("Level 1 - Item 3", "https://picsum.photos/600/400?random=3"),
    CardNode("Level 1 - Item 4", "https://picsum.photos/600/400?random=4", children = listOf(
        CardNode("Level 2 - Item 4.1", "https://picsum.photos/600/400?random=41", children = listOf(
             CardNode("Level 3 - Item 4.1.1", "https://picsum.photos/600/400?random=411", children = listOf(
                CardNode("Level 4 - Final Item 5", "https://picsum.photos/600/400?random=4111")
            ))
        ))
    )),
    CardNode("Level 1 - Item 5", "https://picsum.photos/600/400?random=5")
)

private fun getNodeFromPath(path: String): CardNode {
    val indices = path.split("_").map { it.toInt() }
    var currentNodeList = cardTreeData
    var node: CardNode = currentNodeList[indices[0]]
    indices.drop(1).forEach { index ->
        node = currentNodeList[index]
        currentNodeList = node.children
    }
    return node
}

private fun getListFromPath(path: String): List<CardNode> {
    if (path.isEmpty()) return cardTreeData
    val indices = path.split("_").map { it.toInt() }
    var currentNodeList = cardTreeData
    indices.forEach { index ->
        currentNodeList = currentNodeList[index].children
    }
    return currentNodeList
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        enableEdgeToEdge()
        
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surfaceContainerHighest) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AppMaterialTheme {
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
            .height(800.dp)
            .clip(RoundedCornerShape(48.dp))
            .shadow(20.dp, shape = RoundedCornerShape(48.dp)),
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
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
                    currentPath = path,
                    contentPadding = innerPadding
                )
            }
            
            composable("detail/{path}") { backStackEntry ->
                val path = backStackEntry.arguments?.getString("path")!!
                val card = getNodeFromPath(path)
                DetailScreen(
                    navController = navController,
                    card = card,
                    contentPadding = innerPadding
                )
            }
        }
    }
}

@Composable
fun AppMaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) dynamicDarkColorScheme(LocalContext.current)
                      else dynamicLightColorScheme(LocalContext.current)

    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}

@OptIn(ExperimentalMaterial3ai::class)
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
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun CardList(
    navController: NavHostController,
    cards: List<CardNode>,
    currentPath: String,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding() + 100.dp,
            bottom = contentPadding.calculateBottomPadding() + 100.dp,
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "This is the shared UI screen. This content is the same for all screens, only the title and image above have changed based on the card you clicked.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
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

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    AppMaterialTheme {
        DetailScreen(
            navController = rememberNavController(),
            card = cardTreeData[0],
            contentPadding = PaddingValues(0.dp)
        )
    }
}
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
import com.sooq.price.data.CardNode
import com.sooq.price.data.getListFromPath
import com.sooq.price.data.getNodeFromPath
import com.sooq.price.ui.DetailScreen

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
            .height(760.dp)
            .clip(RoundedCornerShape(48.dp))
            .shadow(20.dp, shape = RoundedCornerShape(48.dp)),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
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
                style = MaterialTheme. typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
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
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding() + 100.dp,
            bottom = contentPadding.calculateBottomPadding() + 50.dp,
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
    AppMaterialTheme {
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
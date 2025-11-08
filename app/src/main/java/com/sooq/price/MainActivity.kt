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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.style.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import java.text.*
import java.util.*
import coil.compose.*
import coil.request.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        enableEdgeToEdge()
        
        setContent {
            AppMaterialTheme {
                AppShell()
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

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Test Title") },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        CardList(contentPadding = innerPadding)
    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val density = LocalDensity.current

    val initialTopPaddingPx = with(density) { 8.dp.toPx() }

    val customTopOffset = scrollBehavior.state.offset.let { scrollOffset ->
        Math.max(
            0f,
            initialTopPaddingPx + scrollOffset
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = with(density) { customTopOffset.toDp() })
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Test Panel",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(Modifier.weight(1f))
                        // reminder to add an icon here
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            }
        }
    ) { innerPadding ->
        val adjustedContentPadding = innerPadding.copy(
            top = innerPadding.calculateTopPadding() + with(density) { initialTopPaddingPx.toDp() }
        )
        CardList(contentPadding = adjustedContentPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyElevatedCard(title: String, imageUrl: String) {
    ElevatedCard(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(36.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

/*@Composable
fun CardList(contentPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, top = 200.dp, bottom = 100.dp)
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(16) { index ->
            MyElevatedCard(
                title = "Card Title ${index + 1}",
                imageUrl = "https://picsum.photos/600/400?random=$index"
            )
        }
    }
}*/

@Composable
fun CardList(contentPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(16) { index ->
            MyElevatedCard(
                title = "Card Title ${index + 1}",
                imageUrl = "https://picsum.photos/600/400?random=$index"
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
    AppMaterialTheme(darkTheme = false) {
        AppShell()
    }
}

@Preview
@Composable
fun MyElevatedCardPreview() {
    AppMaterialTheme {
        MyElevatedCard(
            title = "Preview Title",
            imageUrl = "https://picsum.photos/600/400?random=0"
        )
    }
}
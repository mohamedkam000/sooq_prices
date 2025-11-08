package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            MaterialTheme {
                AppShell()
            }
        }
    }
}

@Composable
fun MaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("M3 Elevated Cards") },
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
}

@Composable
fun CardList(contentPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(8) { index ->
            MyElevatedCard(
                title = "Card Title ${index + 1}",
                content = "This is the content for card number ${index + 1}. "
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyElevatedCard(title: String, content: String) {
    ElevatedCard(
        onClick = {
        },
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppShellPreview() {
    MaterialTheme {
        AppShell()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkAppShellPreview() {
    MaterialTheme(darkTheme = false) {
        AppShell()
    }
}

@Preview
@Composable
fun MyElevatedCardPreview() {
    MaterialTheme {
        MyElevatedCard(
            title = "Preview Title",
            content = "This is preview content for the card."
        )
    }
}
package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// --- Main Activity ---
// This is the entry point of the app.
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        setContent {
            // Apply the custom M3 theme
            MyApplicationTheme {
                // Main app UI structure
                AppShell()
            }
        }
    }
}

// --- Theme ---
// A simple Material 3 theme wrapper.
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme() // Default M3 dark colors
    } else {
        lightColorScheme() // Default M3 light colors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}

// --- App Shell Composable ---
// Creates the main app structure with a top bar.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppShell() {
    // A scroll behavior for the top app bar
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
        // Pass the padding from the Scaffold to the content
        CardList(contentPadding = innerPadding)
    }
}

// --- Card List Composable ---
// Displays the vertically scrollable list of cards.
@Composable
fun CardList(contentPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = contentPadding, // Apply padding from Scaffold
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Create 5 elevated cards
        items(5) { index ->
            MyElevatedCard(
                title = "Card Title ${index + 1}",
                content = "This is the content for card number ${index + 1}. " +
                          "It demonstrates the Material 3 elevated card design."
            )
        }
    }
}

// --- Elevated Card Composable ---
// Defines the look and content of a single card.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyElevatedCard(title: String, content: String) {
    ElevatedCard(
        onClick = {
            // Handle card click event here
        },
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp // Standard elevation for elevated cards
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

// --- Previews ---
// Enables a preview of the composables in Android Studio.
@Preview(showBackground = true)
@Composable
fun AppShellPreview() {
    MyApplicationTheme {
        AppShell()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkAppShellPreview() {
    MyApplicationTheme(darkTheme = true) {
        AppShell()
    }
}

@Preview
@Composable
fun MyElevatedCardPreview() {
    MyApplicationTheme {
        MyElevatedCard(
            title = "Preview Title",
            content = "This is preview content for the card."
        )
    }
}
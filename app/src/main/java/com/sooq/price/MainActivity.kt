package com.sooq.price

// -------------------- Imports --------------------
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import org.json.JSONObject
import com.sooq.price.ui.MainScreen

// -------------------- Data Model --------------------
data class DataModel(
    val orange_100: String,
    val apple_1: String,
    val grape_k: String,
    val watermelon: String,
    val date: String
)

// -------------------- MainActivity --------------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val context = LocalContext.current

            // -------- Dynamic Colors (Monet) --------
            val colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (isSystemInDarkTheme()) dynamicDarkColorScheme(context)
                else dynamicLightColorScheme(context)
            } else {
                lightColorScheme() // fallback
            }

            // Update status bar & nav bar colors
            SideEffect {
                window.statusBarColor = colorScheme.primary.toArgb()
                window.navigationBarColor = colorScheme.surface.toArgb()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.insetsController?.setSystemBarsAppearance(
                        if (isSystemInDarkTheme()) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            }

            MaterialTheme(colorScheme = colorScheme) {
                var data by remember { mutableStateOf<DataModel?>(null) }
                var isLoading by remember { mutableStateOf(true) }
                var hasError by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    try {
                        data = loadJsonFromUrl(
                            "https://raw.githubusercontent.com/mohamedkam000/prices/main/data.json"
                        )
                        hasError = data == null
                    } catch (e: Exception) {
                        hasError = true
                    } finally {
                        isLoading = false
                    }
                }

                when {
                    isLoading -> LoadingScreen()
                    hasError -> ErrorScreen(onRetry = {
                        isLoading = true
                        hasError = false
                    })
                    else -> {
                        data?.let { d ->
                            val navController = rememberNavController()
                            MainScreen(navController = navController, data = d)
                        }
                    }
                }
            }
        }
    }
}

// -------------------- JSON Loader --------------------
suspend fun loadJsonFromUrl(urlString: String): DataModel? = withContext(Dispatchers.IO) {
    try {
        val jsonText = URL(urlString).readText()
        val json = JSONObject(jsonText)
        DataModel(
            orange_100 = json.getString("orange_100"),
            apple_1 = json.getString("apple_1"),
            grape_k = json.getString("grape_k"),
            watermelon = json.getString("watermelon"),
            date = json.getString("date")
        )
    } catch (e: Exception) {
        null
    }
}

// -------------------- Error & Loading Screens --------------------
@Composable
fun ErrorScreen(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().clickable { onRetry() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "😞 Error loading data. Tap to retry.",
            fontSize = 18.sp
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

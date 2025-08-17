package com.sooq.price

import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.stickyHeader
import androidx.compose.material3.*
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.marketexpressive.ui.theme.MarketExpressiveTheme
import kotlinx.coroutines.delay

import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.animation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.core.*
import androidx.core.content.edit
import com.sooq.price.ui.MainScreen
import com.sooq.price.ui.categories.*
import com.sooq.price.ui.theme.SooqTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            SooqTheme() {
                AppNavigation()
            }
        }
    }
}

@Composable
fun OnboardingPager(onFinish: () -> Unit) {
    var page by remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (page) {
            0 -> OnboardingPage(title = "Welcome", subtitle = "Discover featured categories.")
            1 -> OnboardingPage(title = "Browse", subtitle = "As if you were in the market.")
            2 -> OnboardingPage(title = "Enjoy", subtitle = "Fast animations and expressive theming.")
        }
        Row(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            for (i in 0..2) {
                val active = i == page
                Box(modifier = Modifier
                    .size(if (active) 16.dp else 10.dp)
                    .background(MaterialTheme.colorScheme.background, shape = CircleShape)
                    .clickable { page = i })
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (page < 2) page++ else onFinish()
            }) {
                Text(if (page < 2) "Next" else "Start")
            }
        }
    }
}

@Composable
fun OnboardingPage(title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(80.dp))
        Text(title, fontSize = 34.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text(subtitle)
    }
}

object AppIntroManager {
    private const val PREF_INTRO = "app_prefs"
    private const val PREF_INTRO_SHOWN = "intro_shown"

    fun shouldShowIntro(context: Context): Boolean {
        val sharedPrefs = context.getSharedPreferences(PREF_INTRO, Context.MODE_PRIVATE)
        return !sharedPrefs.getBoolean(PREF_INTRO_SHOWN, false)
    }

    fun markIntroAsCompleted(context: Context) {
        val sharedPrefs = context.getSharedPreferences(PREF_INTRO, Context.MODE_PRIVATE)
        sharedPrefs.edit { putBoolean(PREF_INTRO_SHOWN, true) }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val startDestination = if (AppIntroManager.shouldShowIntro(context)) {
        "intro"
    } else {
        "main"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(animationSpec = tween(750, easing = FastOutSlowInEasing)) +
                    scaleIn(initialScale = 0.9f, animationSpec = tween(300, easing = FastOutSlowInEasing))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300, easing = FastOutSlowInEasing)) +
                    scaleIn(initialScale = 0.9f, animationSpec = tween(300, easing = FastOutSlowInEasing))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300, easing = FastOutSlowInEasing)) +
                    scaleOut(targetScale = 1.1f, animationSpec = tween(300, easing = FastOutSlowInEasing))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300, easing = FastOutSlowInEasing)) +
                    scaleOut(targetScale = 1.1f, animationSpec = tween(300, easing = FastOutSlowInEasing))
        },
    ) {
        composable("intro") { OnboardingPager(navController) }
        composable("main") { MainScreen(navController) }
        composable("footwear") { Footwear(navController) }
    }
}
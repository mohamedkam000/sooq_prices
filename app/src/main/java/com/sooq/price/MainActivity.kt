package com.sooq.price

import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.animation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.core.*
import androidx.core.content.edit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import com.sooq.price.ui.MainScreen
import com.sooq.price.ui.categories.*
import com.sooq.price.appintro.AppIntroScreen
import com.sooq.price.ui.theme.*

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

object AppIntroManager {
    private const val PREF_APP_INTRO = "app_prefs"
    private const val PREF_INTRO_SHOWN = "intro_shown"

    fun shouldShowIntro(context: Context): Boolean {
        val sharedPrefs = context.getSharedPreferences(PREF_APP_INTRO, Context.MODE_PRIVATE)
        return !sharedPrefs.getBoolean(PREF_INTRO_SHOWN, false)
    }

    fun markIntroAsCompleted(context: Context) {
        val sharedPrefs = context.getSharedPreferences(PREF_APP_INTRO, Context.MODE_PRIVATE)
        sharedPrefs.edit { putBoolean(PREF_INTRO_SHOWN, true) }
    }
}

//@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val duration = Duration.milliseconds(300)

    val startDestination = if (AppIntroManager.shouldShowIntro(context)) {
        "intro"
    } else {
        "main"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(animationSpec = tween(duration)) +
                    scaleIn(initialScale = 0.9f, animationSpec = tween(duration))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(duration)) +
                    scaleIn(initialScale = 0.9f, animationSpec = tween(duration))
        },
    ) {
        composable("intro") { AppIntroScreen(navController) }
        composable("main") { MainScreen(navController) }
        composable("automotive") { Automotive(navController) }
        composable("asset") { Asset(navController) }
        composable("beverage") { Beverage(navController) }
        composable("construction") { Construction(navController) }
        composable("footwear") { Footwear(navController) }
        composable("cloth") { Cloth(navController) }
//        composable("settings") { Settings() }
    }
}
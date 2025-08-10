package com.sooq.price.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.core.content.edit
import com.sooq.price.ui.MainScreen
import com.sooq.price.ui.categories.*
import com.sooq.price.appintro.AppIntroScreen

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

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val startDestination = if (AppIntroManager.shouldShowIntro(context)) {
        "intro"
    } else {
        "main"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("intro") { AppIntroScreen(navController) }
        composable("main") { MainScreen(navController) }
        composable("automotive") { Automotive(navController) }
//        composable("food") { Food() }
        composable("beverage") { Beverage(navController) }
//        composable("electronic") { Electronic() }
        composable("construction") { Construction(navController) }
        composable("footwear") { Footwear(navController) }
        composable("cloth") { Cloth(navController) }
/*        composable("fuel") { Fuel() }
        composable("furniture") { Furniture() }
        composable("tool") { Tool() }
        composable("toy") { Toy() }*/
    }
}
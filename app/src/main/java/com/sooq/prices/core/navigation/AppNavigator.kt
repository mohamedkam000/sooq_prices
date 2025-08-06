package com.sooq.prices.core.navigation

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sooq.prices.features.appintro.ui.AppIntroScreen
import com.sooq.prices.features.settings.ui.SettingsScreen

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String) {
    val duration = 400

    val application = LocalContext.current.applicationContext as AppLockApplication

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
        composable(Screen.AppIntro.route) { AppIntroScreen(navController) }
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
    }
}
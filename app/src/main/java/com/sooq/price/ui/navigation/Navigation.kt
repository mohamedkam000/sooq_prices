package com.sooq.price.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sooq.price.ui.MainScreen
import com.sooq.price.ui.categories.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("automotive") { Automotive() }
        composable("food") { Food() }
        composable("beverage") { Beverage() }
    }
}
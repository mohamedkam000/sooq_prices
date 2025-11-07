package com.sooq.price.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument

import com.sooq.price.data.*
@Composable
fun AppNav(repository: Repository) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "states") {
        composable("states") { StatesScreen(navController) }
        composable("markets/{state}", arguments = listOf(navArgument("state") { type = NavType.StringType })) { backStack ->
            val state = backStack.arguments?.getString("state") ?: ""
            MarketsScreen(navController, state)
        }
        composable("categories/{state}/{market}") { backStack ->
            val state = backStack.arguments?.getString("state") ?: ""
            val market = backStack.arguments?.getString("market") ?: ""
            CategoriesScreen(navController, state, market)
        }
        composable("items/{state}/{market}/{category}") { backStack ->
            val state = backStack.arguments?.getString("state") ?: ""
            val market = backStack.arguments?.getString("market") ?: ""
            val category = backStack.arguments?.getString("category") ?: ""
            ItemsScreen(navController, repository, state, market, category)
        }
        composable("detail/{itemKey}") { backStack ->
            val key = backStack.arguments?.getString("itemKey") ?: ""
            DetailScreen(navController, repository, key)
        }
    }
}
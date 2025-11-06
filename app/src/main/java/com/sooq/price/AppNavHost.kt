package com.sooq.price

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.sooq.price.ui.screens.*

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "states") {
        composable("states") {
            StatesScreen(onStateClick = { state ->
                navController.navigate("markets/$state")
            })
        }
        composable(
            "markets/{state}",
            arguments = listOf(navArgument("state") { type = NavType.StringType })
        ) { backStackEntry ->
            val state = backStackEntry.arguments?.getString("state")!!
            MarketsScreen(
                state = state,
                onMarketClick = { market ->
                    navController.navigate("categories/$state/$market")
                }
            )
        }
        composable(
            "categories/{state}/{market}",
            arguments = listOf(
                navArgument("state") { type = NavType.StringType },
                navArgument("market") { type = NavType.StringType }
            )
        ) { entry ->
            val state = entry.arguments?.getString("state")!!
            val market = entry.arguments?.getString("market")!!
            CategoriesScreen(
                state = state,
                market = market,
                onCategoryClick = { category ->
                    navController.navigate("items/$state/$market/$category")
                }
            )
        }
        composable(
            "items/{state}/{market}/{category}",
            arguments = listOf(
                navArgument("state") { type = NavType.StringType },
                navArgument("market") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType }
            )
        ) { entry ->
            val state = entry.arguments?.getString("state")!!
            val market = entry.arguments?.getString("market")!!
            val category = entry.arguments?.getString("category")!!
            ItemsScreen(
                state = state,
                market = market,
                category = category,
                onItemClick = { itemName ->
                    navController.navigate("price/$state/$market/$category/$itemName")
                }
            )
        }
        composable(
            "price/{state}/{market}/{category}/{item}",
            arguments = listOf(
                navArgument("state") { type = NavType.StringType },
                navArgument("market") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType },
                navArgument("item") { type = NavType.StringType }
            )
        ) { entry ->
            val item = entry.arguments?.getString("item")!!
            PriceScreen(itemName = item)
        }
    }
}
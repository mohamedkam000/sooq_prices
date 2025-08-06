package com.sooq.prices.core.navigation

sealed class Screen(val route: String) {
    object AppIntro : Screen("app_intro")
    object Main : Screen("main")
    object Settings : Screen("settings")
}
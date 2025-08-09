package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sooq.price.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AppNavigation()

            val systemUiController = rememberSystemUiController()
            val backgroundColor = MaterialTheme.colorScheme.surface
            val darkIcons = backgroundColor.luminance() > 0.5f

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = androidx.compose.ui.graphics.Color.Transparent,
                    darkIcons = darkIcons
                )
                systemUiController.setNavigationBarColor(
                    color = MaterialTheme.colorScheme.surface,
                    darkIcons = darkIcons
                )
            }
        }
    }
}
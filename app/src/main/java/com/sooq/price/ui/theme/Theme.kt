package com.sooq.price.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun SooqTheme(
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (dynamicColor) dynamicLightColorScheme(androidx.compose.ui.platform.LocalContext.current)
        else lightColorScheme(
            primary = Color(0xFF06B6D4),
            secondary = Color(0xFF8B5CF6),
            background = Color(0xFFF6F8FB),
            surface = Color(0xFFFFFFFF)
        ),
        typography = Typography(),
        content = content
    )
}
package com.sooq.appintro

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class IntroPage(
    val title: String,
    val description: String,
    val icon: ImageVector? = null,
    val backgroundColor: Color = Color.White,
    val contentColor: Color = Color.Black,
    val illustration: (@Composable () -> Unit)? = null,
    val customContent: (@Composable () -> Unit)? = null,
    val onNext: (() -> Boolean)? = null
)
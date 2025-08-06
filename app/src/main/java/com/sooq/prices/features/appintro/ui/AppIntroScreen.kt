package com.sooq.prices.features.appintro.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.sooq.appintro.AppIntro
import com.sooq.appintro.IntroPage
import com.sooq.prices.core.navigation.Screen
import com.sooq.prices.features.appintro.domain.AppIntroManager

@Composable
fun MethodSelectionCard(
    title: String,
    description: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.White.copy(alpha = 0.2f) else Color.White.copy(
                alpha = 0.1f
            )
        ),
        border = if (isSelected) BorderStroke(2.dp, Color.White) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
            RadioButton(
                selected = isSelected,
                onClick = { onClick() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.White,
                    unselectedColor = Color.White.copy(alpha = 0.6f)
                )
            )
        }
    }
}

@Composable
fun AppIntroScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    val onFinishCallback = {
        AppIntroManager.markIntroAsCompleted(context)
    }

    val basicPages = listOf(
        IntroPage(
            title = "Welcome to Sooq Price",
            description = "Here, you'll get a daily updated prices of items in the Sudanese market.",
            backgroundColor = Color(0xFF00A5FF),
            contentColor = Color.White,
            onNext = { true }
        ),
        IntroPage(
            title = "Comulative Work",
            description = "The prices are based on info provided by trusted merchants across the city.",
            backgroundColor = Color(0xFF00A5FF),
            contentColor = Color.White,
            onNext = { true }
        )
    )

    val finalPage = IntroPage(
        title = "Informative",
        description = "We are giving you the prices, but we can't force merchants to sell to you according to the prices mentioned",
        backgroundColor = Color(0xFF0047AB),
        contentColor = Color.White
        onNext = { true }
    )

    val allPages = basicPages + methodSelectionPage + methodSpecificPages + finalPage

    AppIntro(
        pages = allPages,
        onSkip = {
            AppIntroManager.markIntroAsCompleted(context)
        },
        onFinish = onFinishCallback,
        showSkipButton = false,
        useAnimatedPager = true,
        nextButtonText = "Next",
        finishButtonText = "Let's Go"
    )
}
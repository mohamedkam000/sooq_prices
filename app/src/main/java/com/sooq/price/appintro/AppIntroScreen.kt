package com.sooq.price.appintro

import android.content.Context
import android.content.Intent
import android.os.Build
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
import com.sooq.price.appintro.AppIntro
import com.sooq.price.AppIntroManager
import com.sooq.price.R
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants

data class IntroPage(
    val title: String,
    val description: String,
    val icon: (@Composable () -> Unit)? = null,
    val backgroundColor: Color? = null,
    val contentColor: Color? = null,
    val illustration: (@Composable () -> Unit)? = null,
    val customContent: (@Composable () -> Unit)? = null,
    val onNext: (() -> Boolean)? = null
)

@Composable
fun GearLottieIcon() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gear))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.size(100.dp)
    )
}

@Composable
fun AppIntroScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val onFinishCallback = {
        AppIntroManager.markIntroAsCompleted(context)
        navController.navigate("main") {
            popUpTo("intro") { inclusive = true }
        }
    }

    val basicPages = listOf(
        IntroPage(
            title = "Welcome to Sooq Price!",
            description = "This is your new best app.",
            icon = { GearLottieIcon() },
            backgroundColor = Color(0xFF00E13C),
            contentColor = Color.White,
            onNext = { true }
        ),
        IntroPage(
            title = "Test page",
            description = "This is a placeholder.",
            backgroundColor = Color(0xFFFFA700),
            contentColor = Color.White,
            onNext = { true }
        )
    )

    val finalPage = IntroPage(
        title = "Placeholder",
        description = "This is a second placeholder.",
        backgroundColor = Color(0xFF00A5FF),
        contentColor = Color.White,
        onNext = { true }
    )

    val allPages = basicPages + finalPage

    AppIntro(
        pages = allPages,
        onSkip = {
            AppIntroManager.markIntroAsCompleted(context)
            navController.navigate("main") {
                popUpTo("intro") { inclusive = true }
            }
        },
        onFinish = onFinishCallback,
        showSkipButton = false,
        useAnimatedPager = true,
        nextButtonText = "Next",
        finishButtonText = "Get Started"
    )
}
package com.sooq.prices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                WelcomeScreen()
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    Text("Welcome to Sooq Prices!")
}

@Preview
@Composable
fun PreviewWelcome() {
    MaterialTheme {
        WelcomeScreen()
    }
}
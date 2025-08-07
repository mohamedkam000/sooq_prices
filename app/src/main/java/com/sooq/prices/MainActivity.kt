package com.sooq.prices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("screen1") { ScreenOne() }
        composable("screen2") { ScreenTwo() }
        composable("screen3") { ScreenThree() }
        composable("screen4") { ScreenFour() }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { navController.navigate("screen1") },
            colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4CAF50).copy(alpha = 0.7f),
            contentColor = Color.White
    ),
            modifier = Modifier
                .weight(1f)
                .height(150.dp)
        ) {
            Text("Go to Screen 1", fontSize = 18.sp)
        }

        Button(
            onClick = { navController.navigate("screen2") },
            containerColor = Color(0xFF4CAF50).copy(alpha = 0.7f),
            contentColor = Color.White
    ),
            modifier = Modifier
                .weight(1f)
                .height(150.dp)
        ) {
            Text("Go to Screen 2", fontSize = 18.sp)
        }

        Button(
            onClick = { navController.navigate("screen3") },
            colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4CAF50).copy(alpha = 0.7f),
            contentColor = Color.White
    ),
            modifier = Modifier
                .weight(1f)
                .height(150.dp)
        ) {
            Text("Go to Screen 3", fontSize = 18.sp)
        }

        Button(
            onClick = { navController.navigate("screen4") },
            containerColor = Color(0xFF4CAF50).copy(alpha = 0.7f),
            contentColor = Color.White
    ),
            modifier = Modifier
                .weight(1f)
                .height(150.dp)
        ) {
            Text("Go to Screen 4", fontSize = 18.sp)
        }
    }
}

@Composable
fun ScreenOne() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 1")
    }
}

@Composable
fun ScreenTwo() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 2")
    }
}

@Composable
fun ScreenThree() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 3")
    }
}

@Composable
fun ScreenFour() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 4")
    }
}
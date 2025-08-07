package com.sooq.prices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.text.font.FontWeight
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("screen1") { ScreenOne() }
        composable("screen2") { ScreenTwo() }
        composable("screen3") { ScreenThree() }
        composable("screen4") { ScreenFour() }
        composable("screen5") { ScreenFive() }
        composable("screen6") { ScreenSix() }
        composable("screen7") { ScreenSeven() }
        composable("screen8") { ScreenEight() }
        composable("screen9") { ScreenNine() }
        composable("screen10") { ScreenTen() }
        composable("screen11") { ScreenEleven() }
        composable("screen12") { ScreenTwelve() }
        composable("screen13") { ScreenThirteen() }
        composable("screen14") { ScreenFourteen() }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()
    val useDarkIcons = !darkTheme
    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = useDarkIcons
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Sooq Price",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("screen1") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 2", fontSize = 18.sp)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("screen3") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 4", fontSize = 18.sp)
            }
        }
    
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("screen5") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 5", fontSize = 18.sp)
            }

            Button(
                onClick = { navController.navigate("screen6") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 6", fontSize = 18.sp)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("screen7") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 7", fontSize = 18.sp)
            }

            Button(
                onClick = { navController.navigate("screen8") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 8", fontSize = 18.sp)
            }
        }
    
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("screen9") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 9", fontSize = 18.sp)
            }

            Button(
                onClick = { navController.navigate("screen10") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 10", fontSize = 18.sp)
            }
        }
    
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("screen11") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 11", fontSize = 18.sp)
            }

            Button(
                onClick = { navController.navigate("screen12") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 12", fontSize = 18.sp)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("screen13") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 13", fontSize = 18.sp)
            }

            Button(
                onClick = { navController.navigate("screen14") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6AC9B4).copy(alpha = 0.5f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            ) {
                Text("Go to Screen 14", fontSize = 18.sp)
            }
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

@Composable
fun ScreenFive() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 5")
    }
}

@Composable
fun ScreenSix() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 6")
    }
}

@Composable
fun ScreenSeven() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 7")
    }
}

@Composable
fun ScreenEight() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 8")
    }
}

@Composable
fun ScreenNine() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 9")
    }
}

@Composable
fun ScreenTen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 10")
    }
}

@Composable
fun ScreenEleven() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 11")
    }
}

@Composable
fun ScreenTwelve() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 12")
    }
}

@Composable
fun ScreenThirteen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 13")
    }
}

@Composable
fun ScreenFourteen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("This is Screen 14")
    }
}
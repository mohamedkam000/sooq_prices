package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 20.dp, end = 20.dp, bottom = 48.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Shell(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeaderRow(title = "Market Prices", subtitle = "Pick a state to view markets")
                SampleCardsRow()
            }
        }
    }
}

@Composable
fun Shell(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val maxWidthDp = 1200.dp

    val shellBrush = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.02f),
            Color.White.copy(alpha = 0.01f)
        )
    )

    Box(
        modifier = modifier
            .wrapContentHeight()
            .widthIn(min = 0.dp, max = maxWidthDp)
            .shadow(elevation = 30.dp, shape = RoundedCornerShape(32.dp), clip = false)
            .background(brush = shellBrush, shape = RoundedCornerShape(32.dp))
            .then(
                Modifier.blur(10.dp, edgeTreatment = androidx.compose.ui.draw.BlurEdgeTreatment.Unbounded)
            )
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
}

@Composable
fun HeaderRow(title: String, subtitle: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
        )
    }
}

@Composable
fun SampleCardsRow() {
    val spacing = 16.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        RepeatCard("Khartoum", "20 markets")
        RepeatCard("River Nile", "12 markets")
        RepeatCard("Red Sea", "8 markets")
    }
}

@Composable
fun RepeatCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .weight(1f)
            .height(120.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.03f),
                            Color.White.copy(alpha = 0.01f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
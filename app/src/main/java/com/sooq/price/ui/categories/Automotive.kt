package com.sooq.price.ui.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Automotive() {
        val backgroundColor = MaterialTheme.colorScheme.surfaceVariant

    val scrollState = rememberScrollState()
    val maxFontSize = 34.sp
    val minFontSize = 20.sp
    val maxTopPadding = 40.dp
    val minTopPadding = 0.dp
    val spacerHeight = 150.dp
    val collapseRangeDp = 200.dp
    val density = LocalDensity.current
    val spacerPx = with(density) { spacerHeight.toPx() }
    val collapseRangePx = with(density) { collapseRangeDp.toPx() }
    val collapseFraction = ((scrollState.value - spacerPx) / collapseRangePx).coerceIn(0f, 1f)
    val animatedFontSize = lerp(maxFontSize, minFontSize, collapseFraction)
    val animatedTopPadding = lerp(maxTopPadding, minTopPadding, collapseFraction)
    val titleAlpha by animateFloatAsState(targetValue = collapseFraction)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            Text(
                text = "Hello There!",
                fontSize = animatedFontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = animatedTopPadding)
            )

            Spacer(modifier = Modifier.height(100.dp))
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(WindowInsets.statusBars.asPaddingValues())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("automotive") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = R.drawable.car),
                                contentDescription = "Automotive Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.4f))
                            )
                            Text(
                                text = stringResource(id = R.string.automotive),
                                style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp)
                            )
                        }
                    }
                }
    
                Spacer(modifier = Modifier.height(48.dp))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(backgroundColor)
                .padding(
                    start = 16.dp,
                    bottom = 12.dp
                )
        ) {
            Text(
                text = "Good Morning!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = titleAlpha),
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
    }
}
package com.example.smaro.ui.play.matchtoearn

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.*
import com.example.smaro.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MatchToEarnScreen(
    onGameFinished: (matchedCount: Int, earnedXp: Int) -> Unit
) {
    val pairs = remember { MatchQuestionBank.getPairs() }
    val leftItems = remember { pairs }
    val rightItems = remember { pairs.shuffled() }

    var selectedLeftId by remember { mutableStateOf<Int?>(null) }
    var selectedRightId by remember { mutableStateOf<Int?>(null) }
    var matchedIds by remember { mutableStateOf(setOf<Int>()) }
    var earnedXp by remember { mutableStateOf(0) }
    var gameCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(selectedLeftId, selectedRightId) {
        if (selectedLeftId != null && selectedRightId != null) {
            delay(250)
            if (selectedLeftId == selectedRightId) {
                matchedIds = matchedIds + selectedLeftId!!
                earnedXp += 2
            }
            selectedLeftId = null
            selectedRightId = null
            if (matchedIds.size == pairs.size) gameCompleted = true
        }
    }

    LaunchedEffect(gameCompleted) {
        if (gameCompleted) {
            delay(300)
            onGameFinished(matchedIds.size, earnedXp + 5)
        }
    }

    /* 🔹 LOTTIE */
    val compositionResult = rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.searching)
    )
    val composition = compositionResult.value

    val imageOffsetX = remember { Animatable(0f) }
    val lottieOffsetX = remember { Animatable(0f) }
    val lottieAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        lottieAlpha.animateTo(1f, tween(200))
        delay(350)

        coroutineScope {
            launch {
                imageOffsetX.animateTo(
                    targetValue = -70f,
                    animationSpec = tween(durationMillis = 1000)
                )
            }
            launch {
                lottieOffsetX.animateTo(
                    targetValue = 70f,
                    animationSpec = tween(durationMillis = 1000)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF3B1C7A),
                        Color(0xFF4F5BD5),
                        Color(0xFFFFB347)
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "MATCH TO EARN CHALLENGE",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        /* 🎬 IMAGE + LOTTIE (COMPACT) */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {

            if (composition != null) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(200.dp)
                        .offset {
                            IntOffset(
                                lottieOffsetX.value.dp.roundToPx(),
                                0
                            )
                        }
                        .alpha(lottieAlpha.value)
                        .zIndex(0f)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.matchtoearncircle),
                contentDescription = null,
                modifier = Modifier
                    .size(260.dp)
                    .offset {
                        IntOffset(
                            imageOffsetX.value.dp.roundToPx(),
                            0
                        )
                    }
                    .zIndex(1f)
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Match each concept with its correct definition.\nTap one from left and one from right.",
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        /* ---------- MATCH BOARD ---------- */
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF2A2F45)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    leftItems.forEach { item ->
                        MatchCard(
                            text = item.leftText,
                            isMatched = matchedIds.contains(item.id),
                            isSelected = selectedLeftId == item.id,
                            onClick = {
                                if (!matchedIds.contains(item.id)) {
                                    selectedLeftId = item.id
                                }
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rightItems.forEach { item ->
                        MatchCard(
                            text = item.rightText,
                            isMatched = matchedIds.contains(item.id),
                            isSelected = selectedRightId == item.id,
                            onClick = {
                                if (!matchedIds.contains(item.id)) {
                                    selectedRightId = item.id
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchCard(
    text: String,
    isMatched: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = when {
        isMatched -> Color(0xFF2ECC71)
        isSelected -> Color(0xFF5DA9E9)
        else -> Color(0xFF1F2335)
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

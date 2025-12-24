package com.example.smaro.ui.play.quickquiz

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun QuickQuizScreen(
    onQuizFinished: (Int) -> Unit
) {
    val questions = remember { QuickQuizQuestionBank.questions }

    var currentIndex by remember { mutableStateOf(0) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableStateOf(0) }
    var showAnswer by remember { mutableStateOf(false) }

    val currentQuestion = questions[currentIndex]

    if (showAnswer) {
        LaunchedEffect(currentIndex, showAnswer) {
            delay(700)
            if (currentIndex == questions.lastIndex) {
                onQuizFinished(score)
            } else {
                currentIndex++
                selectedIndex = null
                showAnswer = false
            }
        }
    }

    // Lottie
    val compositionResult = rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.brainyquestions)
    )
    val composition = compositionResult.value

    // Animation states
    val imageOffsetX = remember { Animatable(0f) }
    val lottieOffsetX = remember { Animatable(0f) }
    val lottieAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // 1️⃣ Lottie appears (falling motion handled by JSON)
        lottieAlpha.animateTo(1f, tween(250))

        // 2️⃣ Let it "settle" behind image
        delay(400)

        // 3️⃣ Parallel smooth split
        coroutineScope {
            launch {
                imageOffsetX.animateTo(
                    targetValue = -80f,
                    animationSpec = tween(durationMillis = 1100)
                )
            }
            launch {
                lottieOffsetX.animateTo(
                    targetValue = 80f,
                    animationSpec = tween(durationMillis = 1100)
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
                        Color(0xFF7B2FF7),
                        Color(0xFF4FACFE),
                        Color(0xFFFFB347)
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(32.dp))

        Text(
            text = "QUICK QUIZ CHALLENGE",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        // 🎬 IMAGE + LOTTIE STACK
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {

            // Lottie BACK layer
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

            // Image FRONT layer
            Image(
                painter = painterResource(id = R.drawable.quickquizcircle),
                contentDescription = null,
                modifier = Modifier
                    .size(350.dp)
                    .offset {
                        IntOffset(
                            imageOffsetX.value.dp.roundToPx(),
                            0
                        )
                    }
                    .zIndex(1f)
            )
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2F42))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Q${currentIndex + 1}.",
                    color = Color(0xFFFFC107),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = currentQuestion.question,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(currentQuestion.options.size) { index ->

                val bgColor = when {
                    showAnswer && index == currentQuestion.correctAnswerIndex ->
                        Color(0xFF2E7D32)
                    showAnswer && index == selectedIndex ->
                        Color(0xFFC62828)
                    else ->
                        Color(0xFF1F2335)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = bgColor),
                    onClick = {
                        if (!showAnswer) {
                            selectedIndex = index
                            showAnswer = true
                            if (index == currentQuestion.correctAnswerIndex) {
                                score++
                            }
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    Color(0xFFFFC107),
                                    RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        Spacer(Modifier.width(14.dp))

                        Text(
                            text = currentQuestion.options[index],
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

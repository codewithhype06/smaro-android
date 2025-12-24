package com.example.smaro.ui.play.recallit

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
fun RecallItScreen(
    onSessionFinished: (recalledCount: Int, earnedXp: Int) -> Unit
) {
    val questions = remember { RecallQuestionBank.getQuestions() }

    var currentIndex by remember { mutableStateOf(0) }
    var showAnswer by remember { mutableStateOf(false) }
    var recalledCorrectly by remember { mutableStateOf(0) }
    var earnedXp by remember { mutableStateOf(0) }
    var sessionCompleted by remember { mutableStateOf(false) }

    val currentItem = questions[currentIndex]

    fun moveNext() {
        if (currentIndex == questions.lastIndex) {
            sessionCompleted = true
        } else {
            currentIndex++
            showAnswer = false
        }
    }

    LaunchedEffect(sessionCompleted) {
        if (sessionCompleted) {
            delay(300)
            onSessionFinished(recalledCorrectly, earnedXp)
        }
    }

    // 🔹 Lottie animation (CORRECT NAME)
    val compositionResult = rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.dataanalysis)
    )
    val composition = compositionResult.value

    val imageOffsetX = remember { Animatable(0f) }
    val lottieOffsetX = remember { Animatable(0f) }
    val lottieAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Lottie appears first (falling motion handled by JSON)
        lottieAlpha.animateTo(1f, tween(250))
        delay(400)

        // Parallel smooth movement
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
                        Color(0xFF6A11CB),
                        Color(0xFF2575FC),
                        Color(0xFFFFB347)
                    )
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        Spacer(Modifier.height(12.dp))

        Text(
            text = "RECALL IT CHALLENGE",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        // 🎬 IMAGE + LOTTIE STACK
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {

            if (composition != null) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(180.dp)
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
                painter = painterResource(R.drawable.recallitcircle),
                contentDescription = null,
                modifier = Modifier
                    .size(340.dp)
                    .offset {
                        IntOffset(
                            imageOffsetX.value.dp.roundToPx(),
                            0
                        )
                    }
                    .zIndex(1f)
            )
        }

        /* ---------- QUESTION CARD ---------- */
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1F2937)
            )
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Q${currentIndex + 1}",
                    color = Color(0xFFFFC107),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = currentItem.question,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        if (!showAnswer) {

            Text(
                text = "🧠 Think • Recall • Answer ✨",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Button(
                onClick = { showAnswer = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Show Answer")
            }

        } else {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF334155)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("Answer", fontWeight = FontWeight.Bold, color = Color.White)
                    Text(currentItem.answer, color = Color.White)
                }
            }

            Button(
                onClick = {
                    recalledCorrectly++
                    earnedXp += 2
                    moveNext()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF22C55E)
                )
            ) {
                Text("✅ I Recalled Correctly")
            }

            Button(
                onClick = { moveNext() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444)
                )
            ) {
                Text("❌ I Forgot / Wrong")
            }
        }
    }
}

package com.example.smaro.ui.play.quickquiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.smaro.R

@Composable
fun QuickQuizResultScreen(
    earnedXp: Int,
    currentXp: Int,
    totalXp: Int,
    onBackToPlay: () -> Unit
) {
    val totalQuestions = 5
    val correctAnswers = earnedXp.coerceIn(0, totalQuestions)
    val accuracy = correctAnswers / totalQuestions.toFloat()

    val compositionResult = rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.celebration)
    )
    val composition = compositionResult.value

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "QUIZ COMPLETE!",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier.size(260.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = accuracy,
                    strokeWidth = 12.dp,
                    color = Color(0xFF38BDF8),
                    modifier = Modifier.fillMaxSize()
                )

                Image(
                    painter = painterResource(R.drawable.quickquizcircle),
                    contentDescription = null,
                    modifier = Modifier.size(500.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "${(accuracy * 100).toInt()}% Accuracy",
                color = Color.LightGray
            )

            Spacer(Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B))
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Score: $correctAnswers / $totalQuestions",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "LEVEL PROGRESS",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B4B))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MiniStat("XP Gained", earnedXp)
                    MiniStat("Current XP", currentXp)
                    MiniStat("Total XP", totalXp)
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onBackToPlay,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text("Back to Play", fontWeight = FontWeight.Bold)
            }
        }

        if (composition != null) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun MiniStat(title: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = Color.LightGray)
        Spacer(Modifier.height(6.dp))
        Text(value.toString(), color = Color.White, fontWeight = FontWeight.Bold)
    }
}

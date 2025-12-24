package com.example.smaro.ui.play

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smaro.R
import com.example.smaro.viewmodel.XpViewModel

@Composable
fun PlayScreen(
    onStartQuickQuiz: () -> Unit,
    onStartRecallIt: () -> Unit,
    onStartMatchToEarn: () -> Unit,
    xpViewModel: XpViewModel = viewModel()
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /* ---------------- TOP STATS ---------------- */

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    StatCard("🔥", xpViewModel.streak.toString(), "Streak")
                }
                Box(modifier = Modifier.weight(1f)) {
                    StatCard("⭐", xpViewModel.totalXp.toString(), "XP")
                }
                Box(modifier = Modifier.weight(1f)) {
                    StatCard("🏆", xpViewModel.level.toString(), "Level")
                }
            }
        }

        /* ---------------- DAILY CHALLENGE ---------------- */

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF181818)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Daily Challenge", fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Play 1 game today to keep your streak alive",
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "+XP · Streak continues",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4ADE80)
                    )
                }
            }
        }

        /* ---------------- GAMES ---------------- */

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1C1C1E)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text("Games", fontWeight = FontWeight.Bold, color = Color.White)

                    Spacer(Modifier.height(12.dp))

                    MiniGameCard(
                        iconRes = R.drawable.quickquizgraphiconly,
                        title = "Quick Quiz",
                        subtitle = "Fast MCQs",
                        onClick = onStartQuickQuiz
                    )

                    MiniGameCard(
                        iconRes = R.drawable.recallitonlygraphic,
                        title = "Recall It",
                        subtitle = "Memory based",
                        onClick = onStartRecallIt
                    )

                    MiniGameCard(
                        iconRes = R.drawable.matchtoearnonlygraphic,
                        title = "Match To Earn",
                        subtitle = "Match concepts & score",
                        onClick = onStartMatchToEarn
                    )
                }
            }
        }

        /* ---------------- ACHIEVEMENTS ---------------- */

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF181818)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text("Achievements", fontWeight = FontWeight.Bold, color = Color.White)

                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AchievementChip("🏅 Quiz Master", Modifier.weight(1f))
                        AchievementChip("🧠 Recall Champ", Modifier.weight(1f))
                        AchievementChip("🔥 Streak Hero", Modifier.weight(1f))
                    }
                }
            }
        }

        item { Spacer(Modifier.height(32.dp)) }
    }
}

/* ---------------- COMPONENTS ---------------- */

@Composable
private fun StatCard(
    emoji: String,
    value: String,
    label: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, style = MaterialTheme.typography.titleLarge)
            Text(value, fontWeight = FontWeight.Bold, color = Color.White)
            Text(label, color = Color.White.copy(alpha = 0.7f))
        }
    }
}

@Composable
private fun MiniGameCard(
    iconRes: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A3441)
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold, color = Color.White)
                Text(subtitle, color = Color.White.copy(alpha = 0.7f))
            }

            Text("→", fontWeight = FontWeight.Bold, color = Color(0xFF22D3EE))
        }
    }
}

@Composable
private fun AchievementChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A3441)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

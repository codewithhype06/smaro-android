package com.example.smaro.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smaro.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onLearnClick: () -> Unit,
    onPlayClick: () -> Unit,
    onReviseClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 🔥 XP & STREAK
        Card {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "XP: ${viewModel.xp}",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "🔥 Streak: ${viewModel.streak} days",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // 🧠 DAILY RECALL
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Daily Recall", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text(viewModel.dailyRecall.title)
                Text(
                    viewModel.dailyRecall.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // 📘 CONCEPT OF THE DAY
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Concept of the Day", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text(viewModel.conceptOfTheDay)
            }
        }

        // ⚡ QUICK ACTIONS
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Quick Actions", fontWeight = FontWeight.Bold)

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onLearnClick
                ) {
                    Text("Learn")
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onPlayClick
                ) {
                    Text("Play")
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onReviseClick
                ) {
                    Text("Revise")
                }
            }
        }
    }
}

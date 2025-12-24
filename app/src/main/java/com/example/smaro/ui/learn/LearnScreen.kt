package com.example.smaro.ui.learn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.random.Random

/* ---------- SAME COLORS AS HOME ---------- */

private val CardDark = Color(0xFF181818)
private val AccentCyan = Color(0xFF22D3EE)
private val LightGreyText = Color.White.copy(alpha = 0.65f)

/* ===================== LEARN SCREEN ===================== */

@Composable
fun LearnScreen(
    onOpenCapsules: () -> Unit   // ✅ REQUIRED (NO DEFAULT)
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { DailyMotivationCard() }
        item { SmartLearningCapsulesCard(onOpenCapsules) } // ✅ SAFE WIRED
        item { LearnPathCard() }
        item { ExplainInYourWordsCard() }
        item { ConfusionResolverCard() }
        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}

/* ------------------------------------------------ */
/* CARD 1 — DAILY MOTIVATION */
/* ------------------------------------------------ */

@Composable
private fun DailyMotivationCard() {

    val quotes = listOf(
        "Discipline will take you places motivation can't.",
        "Small steps taken daily beat occasional bursts of effort.",
        "Consistency compounds into mastery.",
        "Your future self is watching your choices today.",
        "Learning daily is a silent form of self-respect.",
        "You don’t need intensity, you need continuity.",
        "Progress feels slow until it suddenly isn’t.",
        "One focused hour a day can change your life."
    )

    val quote = remember { quotes[Random.nextInt(quotes.size)] }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(Modifier.padding(18.dp)) {

            Text(
                "Daily Motivation",
                color = AccentCyan,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "“$quote”",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/* ------------------------------------------------ */
/* CARD 2 — SMART LEARNING CAPSULES */
/* ------------------------------------------------ */

@Composable
private fun SmartLearningCapsulesCard(
    onOpenCapsules: () -> Unit
) {
    Card(
        onClick = onOpenCapsules,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(Modifier.padding(18.dp)) {

            Text(
                "Smart Learning Capsules",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                "Create your own learning capsules and revise anytime.",
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "→ Tap to open",
                color = AccentCyan,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/* ------------------------------------------------ */
/* CARD 3 — LEARN PATH */
/* ------------------------------------------------ */

@Composable
private fun LearnPathCard() {
    var goal by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("Beginner") }
    var roadmap by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(Modifier.padding(18.dp)) {

            Text("Learn Path", color = Color.White, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = goal,
                onValueChange = { goal = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Your Goal", color = LightGreyText) },
                textStyle = TextStyle(color = LightGreyText)
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf("Beginner", "Intermediate", "Advanced").forEach {
                    FilterChip(
                        selected = level == it,
                        onClick = { level = it },
                        label = { Text(it) }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { roadmap = generateRoadmap(goal, level) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generate Roadmap")
            }

            roadmap?.let {
                Spacer(Modifier.height(10.dp))
                Text(it, color = Color.White.copy(alpha = 0.8f))
            }
        }
    }
}

private fun generateRoadmap(goal: String, level: String): String {
    if (goal.isBlank()) return "Define a clear goal to generate a roadmap."

    return when (level) {
        "Beginner" -> "• Learn basics of $goal\n• Practice daily\n• Build small projects"
        "Intermediate" -> "• Strengthen concepts\n• Apply in real scenarios\n• Improve consistency"
        else -> "• Master advanced topics\n• Build production-level work\n• Optimize deeply"
    }
}

/* ------------------------------------------------ */
/* CARD 4 — EXPLAIN IN YOUR WORDS */
/* ------------------------------------------------ */

@Composable
private fun ExplainInYourWordsCard() {
    var text by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(Modifier.padding(18.dp)) {

            Text("Explain in Your Words", color = Color.White, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                label = {
                    Text(
                        "Writing in your own words improves memory.",
                        color = LightGreyText
                    )
                },
                textStyle = TextStyle(color = LightGreyText)
            )
        }
    }
}

/* ------------------------------------------------ */
/* CARD 5 — CONFUSION RESOLVER */
/* ------------------------------------------------ */

@Composable
private fun ConfusionResolverCard() {
    var confusion by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(Modifier.padding(18.dp)) {

            Text("Confusion Resolver", color = Color.White, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = confusion,
                onValueChange = { confusion = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                label = { Text("What didn’t you understand?", color = LightGreyText) },
                textStyle = TextStyle(color = LightGreyText)
            )

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                enabled = confusion.isNotBlank()
            ) {
                Text("Save to Weak Zone")
            }
        }
    }
}

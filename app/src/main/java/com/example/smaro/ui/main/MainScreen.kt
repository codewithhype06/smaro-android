package com.example.smaro.ui.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smaro.R
import com.example.smaro.ui.compete.CompeteScreen
import com.example.smaro.ui.home.HomeScreen
import com.example.smaro.ui.learn.LearnScreen
import com.example.smaro.ui.play.PlayScreen
import com.example.smaro.ui.profile.ProfileScreen
import com.example.smaro.viewmodel.XpViewModel
import com.example.smaro.viewmodel.LearningViewModel

@Composable
fun MainScreen(
    onBuildResume: () -> Unit,
    onStartQuickQuiz: () -> Unit,
    onStartRecallIt: () -> Unit,
    onStartMatchToEarn: () -> Unit,
    onOpenDeveloper: () -> Unit,
    onOpenTicTacToe: () -> Unit,
    onOpenFocusPlanner: () -> Unit,
    onOpenTodayLearning: () -> Unit,
    onOpenCapsules: () -> Unit,        // ✅ REQUIRED
    xpViewModel: XpViewModel,
    learningViewModel: LearningViewModel
) {
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.app_bg1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                MiniCardBottomNav(
                    selectedIndex = selectedIndex,
                    onSelect = { selectedIndex = it }
                )
            }
        ) { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                when (selectedIndex) {

                    /* ---------- HOME ---------- */
                    0 -> HomeScreen(
                        onLearnClick = { selectedIndex = 1 },
                        onPlayClick = { selectedIndex = 2 },
                        onReviseClick = { selectedIndex = 3 },
                        onOpenTodaySnapshot = onOpenTodayLearning,
                        onOpenPlanner = onOpenFocusPlanner,
                        onOpenTicTacToe = onOpenTicTacToe,
                        learningViewModel = learningViewModel
                    )

                    /* ---------- LEARN ---------- */
                    1 -> LearnScreen(
                        onOpenCapsules = onOpenCapsules   // ✅ FIXED & CLEAN
                    )

                    /* ---------- PLAY ---------- */
                    2 -> PlayScreen(
                        onStartQuickQuiz = onStartQuickQuiz,
                        onStartRecallIt = onStartRecallIt,
                        onStartMatchToEarn = onStartMatchToEarn,
                        xpViewModel = xpViewModel
                    )

                    /* ---------- COMPETE ---------- */
                    3 -> CompeteScreen()

                    /* ---------- PROFILE ---------- */
                    4 -> ProfileScreen(
                        onBuildResume = onBuildResume,
                        onOpenDeveloper = onOpenDeveloper
                    )
                }
            }
        }
    }
}

/* ================= MINI CARD BOTTOM NAV ================= */

@Composable
private fun MiniCardBottomNav(
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    val items = listOf(
        "🏠" to "Home",
        "📘" to "Learn",
        "▶" to "Play",
        "🏆" to "Compete",
        "👤" to "Profile"
    )

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xCC111111))
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, pair ->
                MiniNavItem(
                    emoji = pair.first,
                    label = pair.second,
                    selected = selectedIndex == index,
                    onClick = { onSelect(index) }
                )
            }
        }
    }
}

@Composable
private fun MiniNavItem(
    emoji: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val lift by animateDpAsState(if (selected) (-8).dp else 0.dp, label = "")
    val scale by animateFloatAsState(if (selected) 1.08f else 1f, label = "")

    Column(
        modifier = Modifier
            .offset(y = lift)
            .scale(scale)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .pointerInput(Unit) {
                detectTapGestures { onClick() }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = if (selected) Color(0xFF22D3EE) else Color.Transparent,
                    shape = RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.titleLarge,
                color = if (selected) Color.Black else Color.White
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) Color.White else Color.White.copy(alpha = 0.6f)
        )
    }
}

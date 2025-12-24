package com.example.smaro.ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.smaro.R
import com.example.smaro.viewmodel.LearningViewModel
import kotlinx.coroutines.delay

/* ---------- COLORS ---------- */

private val CardDark = Color(0xFF181818)
private val AccentCyan = Color(0xFF22D3EE)
private val GlassCard = Color.White.copy(alpha = 0.08f)
private val ChillIconSize = 64.dp
private val SoftYellow = Color(0xFFFFE08A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLearnClick: () -> Unit,
    onPlayClick: () -> Unit,
    onReviseClick: () -> Unit,
    onOpenTodaySnapshot: () -> Unit,
    onOpenPlanner: () -> Unit,
    onOpenTicTacToe: () -> Unit,
    learningViewModel: LearningViewModel
) {
    val context = LocalContext.current

    val recallItems by learningViewModel.todaysRecall.collectAsState()
    val recallItem by remember { derivedStateOf { recallItems.randomOrNull() } }

    fun openAppOrBrowser(pkg: String?, url: String) {
        try {
            val intent = pkg?.let {
                context.packageManager.getLaunchIntentForPackage(it)
            }
            context.startActivity(intent ?: Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (_: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        /* ---------- HEADER (FIXED) ---------- */
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardDark),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(R.drawable.smarotext),
                        contentDescription = null,
                        modifier = Modifier.height(56.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Learn smarter. Remember longer.",
                        color = Color(0xFFFFD166),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Build your career.",
                        color = Color(0xFFFFD166),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                }
            }
        }

        /* ---------- CENTER ANIMATION ---------- */
        item {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                val comp by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.homeanimation)
                )
                LottieAnimation(
                    composition = comp,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(170.dp)
                )
            }
        }

        /* ---------- RECALL QUICKLY ---------- */
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardDark),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text("🔁 Recall Quickly", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    recallItem?.let {
                        Text(it.title, color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text(it.description, color = Color.White.copy(alpha = 0.7f))
                    } ?: Text(
                        "Yesterday’s learning will appear here",
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        }

        /* ---------- ADD TODAY'S LEARNING ---------- */
        item {
            var expanded by remember { mutableStateOf(false) }
            val rotation by animateFloatAsState(
                if (expanded) 180f else 0f,
                animationSpec = tween(250),
                label = ""
            )

            Card(
                modifier = Modifier.fillMaxWidth().animateContentSize(),
                colors = CardDefaults.cardColors(containerColor = CardDark)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("Add Today’s Learning", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Tap to add today’s learning", color = Color.White.copy(alpha = 0.6f))
                        }
                        Text(
                            if (expanded) "−" else "+",
                            modifier = Modifier.rotate(rotation).clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { expanded = !expanded },
                            color = AccentCyan,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    AnimatedVisibility(expanded) {
                        DailyLearningInputSection(learningViewModel)
                    }
                }
            }
        }

        /* ---------- QUICK ACTIONS ---------- */
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = onLearnClick, modifier = Modifier.weight(1f)) { Text("Learn") }
                Button(onClick = onPlayClick, modifier = Modifier.weight(1f)) { Text("Play") }
                Button(onClick = onReviseClick, modifier = Modifier.weight(1f)) { Text("Compete") }
            }
        }

        /* ---------- FOCUS + SNAPSHOT ---------- */
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                Card(
                    modifier = Modifier.weight(1f).height(120.dp),
                    onClick = onOpenPlanner,
                    colors = CardDefaults.cardColors(containerColor = CardDark)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("📅 Focus Planner", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Plan syllabus with dates", color = Color.White.copy(alpha = 0.7f))
                        }
                        Text("→", color = AccentCyan)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f).height(120.dp),
                    onClick = onOpenTodaySnapshot,
                    colors = CardDefaults.cardColors(containerColor = CardDark)
                ) {
                    Box(Modifier.fillMaxSize().padding(14.dp)) {
                        Column {
                            Text("📘 Today’s Learning Snapshot", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("View today’s learning", color = Color.White.copy(alpha = 0.7f))
                        }
                        Text("→", color = AccentCyan, modifier = Modifier.align(Alignment.BottomEnd))
                    }
                }
            }
        }

        /* ---------- CHILL ZONE ---------- */
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = GlassCard),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(Modifier.padding(20.dp)) {

                    Text(
                        "Tap any icon & chill for a while 🎧🎮",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF111827)
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Image(
                            painter = painterResource(R.drawable.chillzone),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp).offset(y = (-10).dp)
                        )

                        Spacer(Modifier.width(24.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {

                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                ChillIcon(R.drawable.spotifylogo) {
                                    openAppOrBrowser("com.spotify.music", "https://open.spotify.com")
                                }
                                ChillIcon(R.drawable.youtubelogo) {
                                    openAppOrBrowser("com.google.android.youtube", "https://www.youtube.com")
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                ChillIcon(R.drawable.tictactoelogo) { onOpenTicTacToe() }
                                ChillIcon(R.drawable.ludologo) {
                                    openAppOrBrowser(null, "https://poki.com/en/g/ludo-king")
                                }
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(30.dp)) }
    }
}

/* ---------- ICON ---------- */

@Composable
private fun ChillIcon(icon: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(icon),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(ChillIconSize)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
    )
}

/* ---------- INPUT ---------- */

@Composable
private fun DailyLearningInputSection(
    learningViewModel: LearningViewModel
) {
    var subject by remember { mutableStateOf("") }
    var topic by remember { mutableStateOf("") }
    var showSaved by remember { mutableStateOf(false) }

    LaunchedEffect(showSaved) {
        if (showSaved) {
            delay(2000)
            showSaved = false
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject", color = Color.White.copy(alpha = 0.7f)) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = topic,
            onValueChange = { topic = it },
            label = { Text("Topic", color = Color.White.copy(alpha = 0.7f)) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                learningViewModel.addLearningEntry(
                    title = subject,
                    description = topic,
                    dateMillis = System.currentTimeMillis()
                )
                subject = ""
                topic = ""
                showSaved = true
            },
            enabled = subject.isNotBlank() && topic.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }

        AnimatedVisibility(showSaved) {
            Text(
                "✔ Saved successfully",
                color = SoftYellow,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

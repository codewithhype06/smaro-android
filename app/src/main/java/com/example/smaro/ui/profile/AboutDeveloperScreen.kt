package com.example.smaro.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smaro.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AboutDeveloperScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()

    val linkedInUrl = "https://www.linkedin.com/in/nikhil-kumar-shines"
    val instagramUrl = "https://www.instagram.com/i.nikhil.shines"
    val gmailUrl = "mailto:nikhilshines176@gmail.com"

    val darkCard = Color(0xFF1C1C1E)
    val lightText = Color(0xFFEDEDED)
    val primaryYellow = Color(0xFFFFC107)
    val softYellow = Color(0xFFFFE082)

    /* ---------- GLOW PULSE ---------- */
    var pulse by remember { mutableStateOf(false) }
    val glowAlpha by animateFloatAsState(
        targetValue = if (pulse) 0.65f else 0.35f,
        animationSpec = tween(1200),
        label = "glow"
    )

    LaunchedEffect(Unit) {
        while (true) {
            pulse = !pulse
            delay(1200)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        /* ---------- BACKGROUND ---------- */
        Image(
            painter = painterResource(R.drawable.developerbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0.45f),
                            Color.Black.copy(alpha = 0.75f)
                        )
                    )
                )
        )

        /* ---------- TOP SNACKBAR ---------- */
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /* ---------- PROFILE IMAGE ---------- */
            item {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Still learning. Still building."
                                        )
                                    }
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(
                                        primaryYellow.copy(alpha = glowAlpha),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    Box(
                        modifier = Modifier
                            .size(145.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(
                                        primaryYellow.copy(alpha = glowAlpha + 0.15f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    Image(
                        painter = painterResource(R.drawable.nikhilshineimage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                    )
                }
            }

            /* ---------- NAME CARD (PREMIUM CENTERED) ---------- */
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = darkCard),
                    modifier = Modifier.widthIn(max = 340.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "NIKHIL KUMAR",
                            color = softYellow,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            letterSpacing = 1.sp
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "Android Developer · Data Analyst · AI Enthusiast",
                            color = primaryYellow,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )

                        Spacer(Modifier.height(10.dp))

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = primaryYellow.copy(alpha = 0.14f)
                        ) {
                            Text(
                                text = "Independent Developer",
                                modifier = Modifier.padding(
                                    horizontal = 12.dp,
                                    vertical = 5.dp
                                ),
                                color = primaryYellow,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            /* ---------- EXPANDABLE CARDS ---------- */
            item {
                ExpandableCard(
                    "Why Smaro",
                    "People study daily but struggle to convert learning into careers.\nSmaro bridges this gap through structured learning and memory reinforcement.",
                    darkCard,
                    lightText
                )
            }

            item {
                ExpandableCard(
                    "Vision",
                    "To build Smaro as a personal learning & career OS — helping users grow with clarity and confidence.",
                    darkCard,
                    lightText
                )
            }

            item {
                ExpandableCard(
                    "Smaro Core Features",
                    "Career-first Resume Builder\nActive Recall Engine\nSkill to Career Mapping",
                    darkCard,
                    lightText
                )
            }

            item {
                ExpandableCard(
                    "Developer Background & Skills",
                    "Kotlin · Jetpack Compose\nMaterial 3 · MVVM\nAndroid Performance\nData Analysis · AI Systems",
                    darkCard,
                    lightText
                )
            }

            /* ---------- CONNECT ---------- */
            item {
                Card(
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.12f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            Color.White.copy(alpha = 0.25f),
                            RoundedCornerShape(26.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Connect with Developer",
                            color = Color(0xFFF5F5F5),
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BigSocialIcon(R.drawable.linkedin) {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl)))
                            }
                            BigSocialIcon(R.drawable.instagram) {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl)))
                            }
                            BigSocialIcon(R.drawable.gmail) {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(gmailUrl)))
                            }
                        }
                    }
                }
            }
        }
    }
}

/* ---------- EXPANDABLE CARD ---------- */
@Composable
private fun ExpandableCard(
    title: String,
    content: String,
    bgColor: Color,
    textColor: Color
) {
    var expanded by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = spring(),
        label = "rotate"
    )

    Card(
        onClick = { expanded = !expanded },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (expanded) "−" else "+",
                    color = textColor,
                    modifier = Modifier.rotate(rotation)
                )
            }

            if (expanded) {
                Spacer(Modifier.height(12.dp))
                Text(text = content, color = textColor)
            }
        }
    }
}

/* ---------- SOCIAL ICON ---------- */
@Composable
private fun BigSocialIcon(
    icon: Int,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(70.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
    }
}

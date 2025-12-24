package com.example.smaro.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smaro.R
import com.example.smaro.model.LearningEntry
import com.example.smaro.viewmodel.LearningViewModel
import java.text.SimpleDateFormat
import java.util.*

/* ---------- COLORS ---------- */
private val SoftYellow = Color(0xFFFFE08A)
private val EmptyDark = Color(0xFF1F2937)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayLearningScreen(
    onBack: () -> Unit,
    learningViewModel: LearningViewModel
) {
    BackHandler { onBack() }

    val allLearning: List<LearningEntry> by learningViewModel
        .learningEntries
        .collectAsState(initial = emptyList())

    val todayStart = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    val todayLearning = remember(allLearning) {
        allLearning.filter { it.dateMillis >= todayStart }
    }

    /* ---------- BACKGROUND ---------- */
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.focus),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {

                        /* ✅ MINI CARD BEHIND BACK BUTTON */
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xCC181818)
                            ),
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(44.dp)
                        ) {
                            IconButton(onClick = onBack) {
                                Text("←", color = Color.White)
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {

                /* ---------- SNAPSHOT IMAGE ---------- */
                Image(
                    painter = painterResource(R.drawable.todaysnapshot),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(top = 12.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (todayLearning.isEmpty()) {

                    /* ---------- EMPTY STATE ---------- */
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "📭 No learning added today",
                            color = EmptyDark,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = "Start learning and build your Career 🚀",
                            color = EmptyDark.copy(alpha = 0.7f),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(18.dp))

                        Button(
                            onClick = onBack,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SoftYellow,
                                contentColor = Color.Black
                            )
                        ) {
                            Text("← Back to Home", fontWeight = FontWeight.SemiBold)
                        }
                    }

                } else {

                    /* ---------- LIST ---------- */
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(todayLearning) { entry ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF181818)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {

                                    Text(
                                        text = "📘 ${entry.title}",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    Text(
                                        text = entry.description,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )

                                    Spacer(Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Text(
                                            text = "📅 ${formatDate(entry.dateMillis)}  •  ⏰ ${formatTime(entry.dateMillis)}",
                                            color = SoftYellow,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(24.dp)) }
                    }
                }
            }
        }
    }
}

/* ---------- FORMATTERS ---------- */

private fun formatTime(millis: Long): String {
    return SimpleDateFormat("hh:mm a", Locale.getDefault())
        .format(Date(millis))
}

private fun formatDate(millis: Long): String {
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        .format(Date(millis))
}

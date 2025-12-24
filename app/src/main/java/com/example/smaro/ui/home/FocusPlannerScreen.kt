package com.example.smaro.ui.home

import android.app.DatePickerDialog
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.smaro.R
import com.example.smaro.data.FakeRepository
import java.text.SimpleDateFormat
import java.util.*

/* ---------- LOCAL UI MODEL (SAFE) ---------- */
private data class LocalFocusPlan(
    val topic: String,
    val start: Long,
    val end: Long
)

/* ---------- COLORS ---------- */
private val SoftYellow = Color(0xFFFFE08A)
private val DarkHeader = Color(0xFF1F1F1F)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusPlannerScreen(
    repository: FakeRepository,
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    val context = LocalContext.current
    val formatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }

    var topic by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }

    val savedPlans = remember { mutableStateListOf<LocalFocusPlan>() }

    fun openDatePicker(onPicked: (Long) -> Unit) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, y, m, d ->
                val picked = Calendar.getInstance().apply {
                    set(y, m, d, 0, 0, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                onPicked(picked)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
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
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xCC181818)
                            ),
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(46.dp)
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

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                /* ---------- LOGO (PROPERLY CENTERED) ---------- */
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(R.drawable.focuslogo),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp)
                    )
                }

                /* ---------- INPUT CARD ---------- */
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xCC181818)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            OutlinedTextField(
                                value = topic,
                                onValueChange = { topic = it },
                                label = {
                                    Text(
                                        "Topic / Goal",
                                        color = Color.White.copy(alpha = 0.6f)
                                    )
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.White.copy(alpha = 0.85f)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Button(
                                onClick = { openDatePicker { startDate = it } },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    startDate?.let { "Start: ${formatter.format(Date(it))}" }
                                        ?: "Pick Start Date",
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }

                            Button(
                                onClick = { openDatePicker { endDate = it } },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    endDate?.let { "End: ${formatter.format(Date(it))}" }
                                        ?: "Pick End Date",
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }

                            Button(
                                enabled = topic.isNotBlank() && startDate != null && endDate != null,
                                onClick = {
                                    repository.saveFocusPlan(
                                        topic = topic,
                                        startDateMillis = startDate!!,
                                        endDateMillis = endDate!!
                                    )

                                    savedPlans.add(
                                        LocalFocusPlan(topic, startDate!!, endDate!!)
                                    )

                                    topic = ""
                                    startDate = null
                                    endDate = null
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Save Focus Plan",
                                    color = SoftYellow,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                /* ---------- HEADER TEXT ---------- */
                if (savedPlans.isNotEmpty()) {
                    item {
                        Text(
                            "🔥  YOUR FOCUS GOALS  🔥",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = DarkHeader,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                /* ---------- SAVED PLANS ---------- */
                items(savedPlans) { plan ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF181818)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            Text(
                                "🎯 ${plan.topic}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Start: ${formatter.format(Date(plan.start))}",
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    "End: ${formatter.format(Date(plan.end))}",
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }

                            Text(
                                "⏳ X days left",
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(30.dp)) }
            }
        }
    }
}

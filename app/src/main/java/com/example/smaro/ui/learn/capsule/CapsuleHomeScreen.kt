package com.example.smaro.ui.learn.capsules

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.smaro.R
import com.example.smaro.model.Capsule
import com.example.smaro.viewmodel.CapsuleViewModel

/* ---------- COLORS ---------- */

private val CardDark = Color(0xFF181818)
private val AccentCyan = Color(0xFF22D3EE)
private val LightGreyText = Color.White.copy(alpha = 0.65f)

@Composable
fun CapsuleHomeScreen(
    capsuleViewModel: CapsuleViewModel,
    onCreateCapsule: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var topic by remember { mutableStateOf("") }
    var generatedPrompt by remember { mutableStateOf<String?>(null) }
    var pendingEditorTopic by remember { mutableStateOf<String?>(null) }
    var selectedCapsule by remember { mutableStateOf<Capsule?>(null) }
    var expanded by remember { mutableStateOf(true) }

    val capsules by capsuleViewModel.capsules.collectAsState()

    /* ---------- AUTO NAVIGATION ---------- */
    DisposableEffect(lifecycleOwner, pendingEditorTopic) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && pendingEditorTopic != null) {
                onCreateCapsule(pendingEditorTopic!!)
                pendingEditorTopic = null
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        /* ---------- BACKGROUND ---------- */
        Image(
            painter = painterResource(R.drawable.focus),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        /* ---------- MAIN SCROLL CONTENT ---------- */
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            /* ---------- CREATE CAPSULE ---------- */
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardDark)
                ) {
                    Column(Modifier.padding(18.dp)) {

                        Text(
                            "Create Learning Capsule",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField(
                            value = topic,
                            onValueChange = { topic = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Enter topic", color = LightGreyText) },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(color = LightGreyText)
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = { generatedPrompt = buildCapsulePrompt(topic) },
                            enabled = topic.isNotBlank(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Create Capsule Prompt")
                        }

                        generatedPrompt?.let { prompt ->
                            Spacer(Modifier.height(14.dp))

                            Text(
                                "Generated Prompt",
                                color = AccentCyan,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(prompt, color = Color.White.copy(alpha = 0.8f))

                            Spacer(Modifier.height(12.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                                Button(
                                    onClick = { copyToClipboard(context, prompt) },
                                    modifier = Modifier.weight(1f)
                                ) { Text("Copy") }

                                Button(
                                    onClick = {
                                        copyToClipboard(context, prompt)
                                        pendingEditorTopic = topic
                                        openChatGptWithPrompt(context, prompt)
                                    },
                                    modifier = Modifier.weight(1f)
                                ) { Text("ChatGPT") }
                            }
                        }
                    }
                }
            }

            /* ---------- YOUR CAPSULES (EXPANDABLE CARD ONLY) ---------- */
            item {
                val rotation by animateFloatAsState(
                    if (expanded) 180f else 0f,
                    animationSpec = tween(250),
                    label = ""
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    colors = CardDefaults.cardColors(containerColor = CardDark)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "📚 Your Capsules",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                if (expanded) "−" else "+",
                                modifier = Modifier
                                    .rotate(rotation)
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) { expanded = !expanded },
                                color = AccentCyan,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        AnimatedVisibility(expanded) {
                            Column {
                                Spacer(Modifier.height(12.dp))

                                if (capsules.isEmpty()) {
                                    Text(
                                        "No capsules yet.",
                                        color = Color.White.copy(alpha = 0.6f)
                                    )
                                } else {
                                    capsules.take(10).forEach { capsule ->
                                        Spacer(Modifier.height(10.dp))
                                        Card(
                                            onClick = { selectedCapsule = capsule },
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color(0xFF202020)
                                            )
                                        ) {
                                            Column(Modifier.padding(14.dp)) {
                                                Text(
                                                    capsule.topic,
                                                    color = AccentCyan,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Spacer(Modifier.height(6.dp))
                                                Text(
                                                    capsule.content,
                                                    maxLines = 3,
                                                    color = Color.White.copy(alpha = 0.8f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(120.dp)) }
        }

        /* ---------- FLOATING SHOW ALL BUTTON (BOTTOM) ---------- */
        if (capsules.isNotEmpty()) {
            Button(
                onClick = { /* navigate to show all */ },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0F172A)
                )
            ) {
                Text(
                    "📂 Show All Capsules (${capsules.size})",
                    color = AccentCyan
                )
            }
        }
    }

    /* ---------- FULL SCREEN OVERLAY ---------- */
    selectedCapsule?.let { capsule ->
        Dialog(onDismissRequest = { selectedCapsule = null }) {
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = CardDark)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            capsule.topic,
                            color = AccentCyan,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    item {
                        Text(capsule.content, color = Color.White)
                    }
                    item {
                        Button(
                            onClick = { selectedCapsule = null },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Close")
                        }
                    }
                }
            }
        }
    }
}

/* ---------- HELPERS ---------- */

private fun buildCapsulePrompt(topic: String): String = """
Explain the topic "$topic" as a short learning capsule.
1. Simple definition
2. Easy explanation
3. Real-life analogy
4. Example
5. When to use
6. Common mistake
""".trimIndent()

private fun copyToClipboard(context: Context, text: String) {
    val clipboard =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Capsule", text))
}

private fun openChatGptWithPrompt(context: Context, prompt: String) {
    val url = "https://chat.openai.com/?q=${Uri.encode(prompt)}"
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

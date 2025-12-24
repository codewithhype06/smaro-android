package com.example.smaro.ui.learn.capsules

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smaro.R
import com.example.smaro.viewmodel.CapsuleViewModel

/* ---------- COLORS ---------- */

private val CardDark = Color(0xFF181818)
private val AccentCyan = Color(0xFF22D3EE)
private val LightGreyText = Color.White.copy(alpha = 0.65f)

@Composable
fun CapsuleEditorScreen(
    topic: String,
    capsuleViewModel: CapsuleViewModel,
    onDone: () -> Unit
) {
    val context = LocalContext.current
    var capsuleContent by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.app_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            /* ---------- HEADER ---------- */
            item {
                Card(colors = CardDefaults.cardColors(containerColor = CardDark)) {
                    Column(Modifier.padding(18.dp)) {
                        Text(
                            topic,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Write your capsule",
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            /* ---------- CAPSULE INPUT ---------- */
            item {
                Card(colors = CardDefaults.cardColors(containerColor = CardDark)) {
                    Column(Modifier.padding(18.dp)) {

                        Text("Capsule Content", color = AccentCyan)

                        OutlinedTextField(
                            value = capsuleContent,
                            onValueChange = { capsuleContent = it },
                            minLines = 8,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = LocalTextStyle.current.copy(
                                color = LightGreyText   // ✅ TEXT INSIDE BOX = GREY
                            )
                        )
                    }
                }
            }

            /* ---------- SAVE ---------- */
            item {
                Button(
                    onClick = {
                        capsuleViewModel.addCapsule(topic, capsuleContent)
                        onDone()
                    },
                    enabled = capsuleContent.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Capsule")
                }
            }
        }
    }
}

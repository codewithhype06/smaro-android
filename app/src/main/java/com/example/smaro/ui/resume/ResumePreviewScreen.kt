package com.example.smaro.ui.resume

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.smaro.viewmodel.ProfileViewModel

@Composable
fun ResumePreviewScreen(
    role: String,
    viewModel: ProfileViewModel
) {
    val context = LocalContext.current
    val strength = calculateResumeStrength(viewModel)

    Box(modifier = Modifier.fillMaxSize()) {

        /* ================= RESUME CONTENT ================= */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "Nikhil Kumar",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = role,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            Divider()

            Text("Skills", fontWeight = FontWeight.Bold)
            viewModel.skills.forEach {
                Text("• ${it.name} (${it.level})")
            }

            Divider()

            Text("Projects", fontWeight = FontWeight.Bold)
            viewModel.projects.forEach {
                Text(it.title, fontWeight = FontWeight.Bold)
                Text(it.description)
                Text("Tech: ${it.techStack}", fontSize = 12.sp)
            }

            Divider()

            Text("Internships", fontWeight = FontWeight.Bold)
            viewModel.internships.forEach {
                Text(it.role, fontWeight = FontWeight.Bold)
                Text(it.company)
                Text(it.duration)
            }

            Divider()

            Text("Certificates", fontWeight = FontWeight.Bold)
            viewModel.certificates.forEach {
                Text("${it.title} – ${it.platform} (${it.year})")
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        /* ================= RESUME STRENGTH ================= */
        Card(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier.size(34.dp),
                    shape = CircleShape,
                    color = Color(0xFF2563EB)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$strength%",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Resume Strength",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        /* ================= EXPORT / SHARE ================= */
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            SmallActionCard(
                emoji = "📄",
                label = "Export"
            ) {
                val file = ResumePdfExporter.export(
                    context = context,
                    name = "Nikhil Kumar",
                    role = role,
                    lines = buildResumeLines(viewModel)
                )

                val uri = FileProvider.getUriForFile(
                    context,
                    "com.example.smaro.fileprovider",
                    file
                )

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/pdf")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                context.startActivity(intent)
            }

            SmallActionCard(
                emoji = "📤",
                label = "Share"
            ) {
                val file = ResumePdfExporter.export(
                    context = context,
                    name = "Nikhil Kumar",
                    role = role,
                    lines = buildResumeLines(viewModel)
                )

                val uri = FileProvider.getUriForFile(
                    context,
                    "com.example.smaro.fileprovider",
                    file
                )

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "application/pdf"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                context.startActivity(
                    Intent.createChooser(intent, "Share Resume PDF")
                )
            }
        }
    }
}

/* ================= HELPERS ================= */

private fun buildResumeLines(viewModel: ProfileViewModel): List<String> {
    val lines = mutableListOf<String>()

    viewModel.skills.forEach { lines.add("Skill: ${it.name} (${it.level})") }
    viewModel.projects.forEach { lines.add("Project: ${it.title}") }
    viewModel.internships.forEach { lines.add("Internship: ${it.role} at ${it.company}") }
    viewModel.certificates.forEach { lines.add("Certificate: ${it.title}") }

    return lines
}

@Composable
private fun SmallActionCard(
    emoji: String,
    label: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE5E7EB)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(label, fontSize = 12.sp)
        }
    }
}

private fun calculateResumeStrength(viewModel: ProfileViewModel): Int {
    var score = 0
    if (viewModel.skills.isNotEmpty()) score += 25
    if (viewModel.projects.isNotEmpty()) score += 30
    if (viewModel.internships.isNotEmpty()) score += 25
    if (viewModel.certificates.isNotEmpty()) score += 20
    return score.coerceIn(0, 100)
}

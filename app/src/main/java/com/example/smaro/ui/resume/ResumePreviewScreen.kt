package com.example.smaro.ui.resume

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smaro.viewmodel.ProfileViewModel

@Composable
fun ResumePreviewScreen(
    role: String,
    viewModel: ProfileViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Nikhil Kumar", style = MaterialTheme.typography.headlineMedium)
        Text(role, fontWeight = FontWeight.Bold)

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
            Text("Tech: ${it.techStack}", style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.height(6.dp))
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
    }
}

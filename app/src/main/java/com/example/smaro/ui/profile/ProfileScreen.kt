package com.example.smaro.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smaro.viewmodel.ProfileViewModel
import com.example.smaro.ui.profile.component.ExpandableSection

@Composable
fun ProfileScreen(
    onBuildResume: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 👤 PROFILE HEADER
        item {
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Nikhil Kumar",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Aspiring Software Engineer")
                }
            }
        }

        // 🚀 RESUME BUILDER
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Resume Builder", fontWeight = FontWeight.Bold)
                    Text("Select role → Get instant resume")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = onBuildResume) {
                        Text("Build Resume")
                    }
                }
            }
        }

        // 🧠 SKILLS
        item {
            ExpandableSection(title = "Skills") {
                viewModel.skills.forEach {
                    Text("${it.name} • ${it.level}")
                    Spacer(Modifier.height(6.dp))
                }
            }
        }

        // 💼 PROJECTS
        item {
            ExpandableSection(title = "Projects") {
                viewModel.projects.forEach {
                    Text(it.title, fontWeight = FontWeight.Bold)
                    Text(it.description)
                    Spacer(Modifier.height(8.dp))
                }
            }
        }

        // 🏢 INTERNSHIPS
        item {
            ExpandableSection(title = "Internships") {
                viewModel.internships.forEach {
                    Text(it.role, fontWeight = FontWeight.Bold)
                    Text(it.company)
                    Text(it.duration)
                    Spacer(Modifier.height(8.dp))
                }
            }
        }

        // 📜 CERTIFICATES
        item {
            ExpandableSection(title = "Certificates") {
                viewModel.certificates.forEach {
                    Text(it.title, fontWeight = FontWeight.Bold)
                    Text(it.platform)
                    Spacer(Modifier.height(8.dp))
                }
            }
        }

        // 🔚 BOTTOM SPACE (important for comfort)
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

package com.example.smaro.ui.profile.add

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smaro.model.Project

@Composable
fun AddProjectDialog(
    onDismiss: () -> Unit,
    onSave: (Project) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var techStack by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank()) {
                    onSave(Project(title, description, techStack))
                    onDismiss()
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("Add Project") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(title, { title = it }, label = { Text("Title") })
                OutlinedTextField(description, { description = it }, label = { Text("Description") })
                OutlinedTextField(techStack, { techStack = it }, label = { Text("Tech Stack") })
            }
        }
    )
}

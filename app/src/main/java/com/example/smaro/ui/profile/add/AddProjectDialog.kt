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
    var repoLink by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(
                            Project(
                                title = title,
                                description = description,
                                techStack = techStack,
                                repoLink = repoLink
                            )
                        )
                        onDismiss()
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Add Project") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )

                OutlinedTextField(
                    value = techStack,
                    onValueChange = { techStack = it },
                    label = { Text("Tech Stack") }
                )

                OutlinedTextField(
                    value = repoLink,
                    onValueChange = { repoLink = it },
                    label = { Text("GitHub / Repo Link") },
                    placeholder = { Text("https://github.com/username/repo") },
                    singleLine = true
                )
            }
        }
    )
}

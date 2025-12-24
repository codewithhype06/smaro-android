package com.example.smaro.ui.profile.add

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smaro.model.Skill

@Composable
fun AddSkillDialog(
    onDismiss: () -> Unit,
    onSave: (Skill) -> Unit
) {
    var skillName by remember { mutableStateOf("") }
    var skillLevel by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (skillName.isNotBlank() && skillLevel.isNotBlank()) {
                        onSave(Skill(skillName, skillLevel))
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
        title = { Text("Add Skill") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = skillName,
                    onValueChange = { skillName = it },
                    label = { Text("Skill Name") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = skillLevel,
                    onValueChange = { skillLevel = it },
                    label = { Text("Skill Level") },
                    singleLine = true
                )
            }
        }
    )
}

package com.example.smaro.ui.profile.add

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.smaro.model.Certificate

@Composable
fun AddCertificateDialog(
    onDismiss: () -> Unit,
    onSave: (Certificate) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var driveLink by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(
                            Certificate(
                                title = title,
                                platform = platform,
                                year = year,
                                driveLink = driveLink
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
        title = { Text("Add Certificate") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )

                OutlinedTextField(
                    value = platform,
                    onValueChange = { platform = it },
                    label = { Text("Platform") }
                )

                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text("Year") }
                )

                OutlinedTextField(
                    value = driveLink,
                    onValueChange = { driveLink = it },
                    label = { Text("Drive / PDF Link") },
                    placeholder = { Text("https://drive.google.com/...") },
                    singleLine = true
                )
            }
        }
    )
}

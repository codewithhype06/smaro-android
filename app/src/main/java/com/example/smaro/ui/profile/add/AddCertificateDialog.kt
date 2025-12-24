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

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank()) {
                    onSave(Certificate(title, platform, year))
                    onDismiss()
                }
            }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("Add Certificate") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(title, { title = it }, label = { Text("Title") })
                OutlinedTextField(platform, { platform = it }, label = { Text("Platform") })
                OutlinedTextField(year, { year = it }, label = { Text("Year") })
            }
        }
    )
}

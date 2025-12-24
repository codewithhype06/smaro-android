package com.example.smaro.ui.profile.add

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.smaro.model.Internship

@Composable
fun AddInternshipDialog(
    onDismiss: () -> Unit,
    onSave: (Internship) -> Unit
) {
    var role by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (role.isNotBlank()) {
                    onSave(Internship(role, company, duration, description))
                    onDismiss()
                }
            }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("Add Internship") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(role, { role = it }, label = { Text("Role") })
                OutlinedTextField(company, { company = it }, label = { Text("Company") })
                OutlinedTextField(duration, { duration = it }, label = { Text("Duration") })
                OutlinedTextField(description, { description = it }, label = { Text("Description") })
            }
        }
    )
}

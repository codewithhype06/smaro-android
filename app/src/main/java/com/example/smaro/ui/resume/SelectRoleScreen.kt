package com.example.smaro.ui.resume

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectRoleScreen(
    onRoleEntered: (String) -> Unit
) {
    var role by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Enter Your Job Role",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = role,
            onValueChange = { role = it },
            label = { Text("e.g. Android Developer, Artist, Doctor") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                onRoleEntered(role.ifBlank { "Professional" })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate Resume")
        }
    }
}

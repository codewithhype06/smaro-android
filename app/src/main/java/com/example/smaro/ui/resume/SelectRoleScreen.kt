package com.example.smaro.ui.resume

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectRoleScreen(
    onRoleSelected: (String) -> Unit
) {
    val roles = listOf(
        "Android Developer",
        "Frontend Developer",
        "Backend Developer",
        "Software Engineer"
    )

    var selectedRole by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Select Job Role",
            style = MaterialTheme.typography.headlineSmall
        )

        roles.forEach { role ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(role)
                RadioButton(
                    selected = selectedRole == role,
                    onClick = { selectedRole = role }
                )
            }
        }

        Button(
            onClick = { onRoleSelected(selectedRole) },
            enabled = selectedRole.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate Resume")
        }
    }
}

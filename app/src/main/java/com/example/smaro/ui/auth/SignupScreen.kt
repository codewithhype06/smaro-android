package com.example.smaro.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SignupScreen(
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Signup", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(24.dp))

        Button(onClick = {}) {
            Text("Signup (Mock)")
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onLoginClick) {
            Text("Back to Login")
        }
    }
}

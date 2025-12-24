package com.example.smaro.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignupClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // 🔥 VERY IMPORTANT
                onLoginSuccess()
            }
        ) {
            Text("Login (Mock)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onSignupClick) {
            Text("Create new account")
        }
    }
}

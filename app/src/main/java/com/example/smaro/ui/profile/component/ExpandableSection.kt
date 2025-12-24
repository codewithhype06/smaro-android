package com.example.smaro.ui.profile.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableSection(
    title: String,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                content()
            }
        }
    }
}

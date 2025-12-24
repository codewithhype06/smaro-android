package com.example.smaro.ui.auth

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smaro.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinish: () -> Unit
) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600)
        )
        delay(2000) // ⏱ 2 sec splash
        onFinish()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ✅ LOGO (balanced size)
            Image(
                painter = painterResource(id = R.drawable.splash_logo),
                contentDescription = "Smaro Logo",
                modifier = Modifier
                    .size(350.dp)
                    .alpha(alpha.value)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ TEXT (same visual weight as logo)
            Text(
                text = "Smaro",
                fontSize = 32.sp,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.alpha(alpha.value)
            )
        }
    }
}

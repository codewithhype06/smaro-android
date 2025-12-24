package com.example.smaro.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.smaro.ui.home.HomeScreen
import com.example.smaro.ui.learn.LearnScreen
import com.example.smaro.ui.play.PlayScreen
import com.example.smaro.ui.compete.CompeteScreen
import com.example.smaro.ui.profile.ProfileScreen

@Composable
fun MainScreen(
    onBuildResume: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }

    val labels = listOf("Home", "Learn", "Play", "Compete", "Profile")

    Scaffold(
        bottomBar = {
            NavigationBar {
                labels.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = {
                            when (index) {
                                0 -> Icon(Icons.Default.Home, null)
                                1 -> Icon(Icons.Default.List, null)
                                2 -> Icon(Icons.Default.PlayArrow, null)
                                3 -> Icon(Icons.Default.Star, null)
                                4 -> Icon(Icons.Default.Person, null)
                            }
                        },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) {
        when (selectedIndex) {

            0 -> HomeScreen(
                onLearnClick = { selectedIndex = 1 },
                onPlayClick = { selectedIndex = 2 },
                onReviseClick = { selectedIndex = 1 }
            )

            1 -> LearnScreen()
            2 -> PlayScreen()
            3 -> CompeteScreen()

            4 -> ProfileScreen(
                onBuildResume = onBuildResume
            )
        }
    }
}

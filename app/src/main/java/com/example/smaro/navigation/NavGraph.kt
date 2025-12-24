package com.example.smaro.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.smaro.ui.auth.*
import com.example.smaro.ui.main.MainScreen
import com.example.smaro.ui.resume.SelectRoleScreen
import com.example.smaro.ui.resume.ResumePreviewScreen
import com.example.smaro.viewmodel.ProfileViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val profileViewModel: ProfileViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSignupClick = {
                    navController.navigate("signup")
                }
            )
        }

        composable("signup") {
            SignupScreen {
                navController.popBackStack()
            }
        }

        composable("main") {
            MainScreen(
                onBuildResume = {
                    navController.navigate("select_role")
                }
            )
        }

        composable("select_role") {
            SelectRoleScreen { role ->
                navController.navigate("resume_preview/$role")
            }
        }

        composable("resume_preview/{role}") { backStack ->
            val role = backStack.arguments?.getString("role") ?: ""
            ResumePreviewScreen(
                role = role,
                viewModel = profileViewModel
            )
        }
    }
}

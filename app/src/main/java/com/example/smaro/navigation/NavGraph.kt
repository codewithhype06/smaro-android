package com.example.smaro.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

import com.example.smaro.ui.main.MainScreen
import com.example.smaro.ui.resume.ResumePreviewScreen
import com.example.smaro.ui.resume.SelectRoleScreen
import com.example.smaro.ui.profile.AboutDeveloperScreen
import com.example.smaro.ui.home.TicTacToeScreen
import com.example.smaro.ui.home.FocusPlannerScreen
import com.example.smaro.ui.home.TodayLearningScreen

import com.example.smaro.ui.learn.capsules.CapsuleHomeScreen
import com.example.smaro.ui.learn.capsules.CapsuleEditorScreen

import com.example.smaro.viewmodel.ProfileViewModel
import com.example.smaro.viewmodel.XpViewModel
import com.example.smaro.viewmodel.LearningViewModel
import com.example.smaro.viewmodel.CapsuleViewModel
import com.example.smaro.data.FakeRepository

import com.example.smaro.ui.play.quickquiz.*
import com.example.smaro.ui.play.recallit.*
import com.example.smaro.ui.play.matchtoearn.*

/* ---------------- ROUTES ---------------- */

object Routes {
    const val MAIN = "main"

    const val TIC_TAC_TOE = "tic_tac_toe"
    const val FOCUS_PLANNER = "focus_planner"
    const val TODAY_LEARNING = "today_learning"

    const val CAPSULE_HOME = "capsule_home"
    const val CAPSULE_EDITOR = "capsule_editor"

    const val ROLE_INPUT = "role_input"
    const val RESUME_PREVIEW = "resume_preview"
    const val ABOUT_DEVELOPER = "about_developer"

    const val QUICK_QUIZ = "quick_quiz"
    const val QUICK_QUIZ_RESULT = "quick_quiz_result"

    const val RECALL_IT = "recall_it"
    const val RECALL_IT_RESULT = "recall_it_result"

    const val MATCH_TO_EARN = "match_to_earn"
    const val MATCH_TO_EARN_RESULT = "match_to_earn_result"
}

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    val profileViewModel: ProfileViewModel = viewModel()
    val xpViewModel: XpViewModel = viewModel()
    val learningViewModel: LearningViewModel = viewModel()
    val capsuleViewModel: CapsuleViewModel = viewModel()   // ✅ SINGLE SOURCE

    val fakeRepository = remember { FakeRepository() }

    var enteredRole by rememberSaveable { mutableStateOf("") }
    var recallCorrect by rememberSaveable { mutableStateOf(0) }
    var matchCorrect by rememberSaveable { mutableStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {

        /* ---------------- MAIN ---------------- */
        composable(Routes.MAIN) {
            MainScreen(
                onBuildResume = { navController.navigate(Routes.ROLE_INPUT) },
                onStartQuickQuiz = { navController.navigate(Routes.QUICK_QUIZ) },
                onStartRecallIt = { navController.navigate(Routes.RECALL_IT) },
                onStartMatchToEarn = { navController.navigate(Routes.MATCH_TO_EARN) },
                onOpenDeveloper = { navController.navigate(Routes.ABOUT_DEVELOPER) },
                onOpenTicTacToe = { navController.navigate(Routes.TIC_TAC_TOE) },
                onOpenFocusPlanner = { navController.navigate(Routes.FOCUS_PLANNER) },
                onOpenTodayLearning = { navController.navigate(Routes.TODAY_LEARNING) },
                onOpenCapsules = { navController.navigate(Routes.CAPSULE_HOME) },
                xpViewModel = xpViewModel,
                learningViewModel = learningViewModel
            )
        }

        /* ---------------- CAPSULE HOME ---------------- */
        composable(Routes.CAPSULE_HOME) {
            CapsuleHomeScreen(
                capsuleViewModel = capsuleViewModel,   // ✅ FIX
                onCreateCapsule = { topic ->
                    navController.navigate(
                        "${Routes.CAPSULE_EDITOR}/$topic"
                    )
                }
            )
        }

        /* ---------------- CAPSULE EDITOR ---------------- */
        composable(
            route = "${Routes.CAPSULE_EDITOR}/{topic}",
            arguments = listOf(
                navArgument("topic") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val topic = backStackEntry.arguments
                ?.getString("topic")
                .orEmpty()

            CapsuleEditorScreen(
                topic = topic,
                capsuleViewModel = capsuleViewModel,   // ✅ SAME VM
                onDone = { navController.popBackStack() }
            )
        }

        /* ---------------- OTHERS ---------------- */

        composable(Routes.TIC_TAC_TOE) {
            TicTacToeScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.FOCUS_PLANNER) {
            FocusPlannerScreen(
                repository = fakeRepository,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.TODAY_LEARNING) {
            TodayLearningScreen(
                onBack = { navController.popBackStack() },
                learningViewModel = learningViewModel
            )
        }

        composable(Routes.ROLE_INPUT) {
            SelectRoleScreen { role ->
                enteredRole = role
                navController.navigate(Routes.RESUME_PREVIEW)
            }
        }

        composable(Routes.RESUME_PREVIEW) {
            ResumePreviewScreen(
                role = enteredRole,
                viewModel = profileViewModel
            )
        }

        composable(Routes.ABOUT_DEVELOPER) {
            AboutDeveloperScreen()
        }

        composable(Routes.QUICK_QUIZ) {
            QuickQuizScreen {
                xpViewModel.addXp(it)
                navController.navigate(Routes.QUICK_QUIZ_RESULT)
            }
        }

        composable(Routes.QUICK_QUIZ_RESULT) {
            QuickQuizResultScreen(
                earnedXp = xpViewModel.lastEarnedXp,
                currentXp = xpViewModel.currentXp,
                totalXp = xpViewModel.totalXp,
                onBackToPlay = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.MAIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.RECALL_IT) {
            RecallItScreen { correct, xp ->
                recallCorrect = correct
                xpViewModel.addXp(xp)
                navController.navigate(Routes.RECALL_IT_RESULT)
            }
        }

        composable(Routes.RECALL_IT_RESULT) {
            RecallItResultScreen(
                recalledCorrectly = recallCorrect,
                totalQuestions = 5,
                earnedXp = xpViewModel.lastEarnedXp,
                currentXp = xpViewModel.currentXp,
                totalXp = xpViewModel.totalXp,
                onBackToPlay = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.MAIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.MATCH_TO_EARN) {
            MatchToEarnScreen { matched, xp ->
                matchCorrect = matched
                xpViewModel.addXp(xp)
                navController.navigate(Routes.MATCH_TO_EARN_RESULT)
            }
        }

        composable(Routes.MATCH_TO_EARN_RESULT) {
            MatchToEarnResultScreen(
                matchedCount = matchCorrect,
                totalPairs = 5,
                earnedXp = xpViewModel.lastEarnedXp,
                currentXp = xpViewModel.currentXp,
                totalXp = xpViewModel.totalXp,
                onBackToPlay = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.MAIN) { inclusive = true }
                    }
                }
            )
        }
    }
}

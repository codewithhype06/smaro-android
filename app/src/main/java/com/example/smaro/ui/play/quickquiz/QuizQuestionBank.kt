package com.example.smaro.ui.play.quickquiz

object QuickQuizQuestionBank {

    val questions = listOf(
        QuizQuestion(
            question = "Kotlin is developed by?",
            options = listOf(
                "Google",
                "Microsoft",
                "JetBrains",
                "Apple"
            ),
            correctAnswerIndex = 2
        ),
        QuizQuestion(
            question = "Which language is officially used for Android?",
            options = listOf(
                "Java",
                "Kotlin",
                "Swift",
                "Python"
            ),
            correctAnswerIndex = 1
        ),
        QuizQuestion(
            question = "Jetpack Compose is used for?",
            options = listOf(
                "Backend",
                "UI",
                "Database",
                "Testing"
            ),
            correctAnswerIndex = 1
        ),
        QuizQuestion(
            question = "Which company owns Android?",
            options = listOf(
                "Apple",
                "Microsoft",
                "Google",
                "Amazon"
            ),
            correctAnswerIndex = 2
        ),
        QuizQuestion(
            question = "Kotlin runs on?",
            options = listOf(
                "JVM",
                "Browser",
                "iOS",
                "Hardware"
            ),
            correctAnswerIndex = 0
        )
    )
}

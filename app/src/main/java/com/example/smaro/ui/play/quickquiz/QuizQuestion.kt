package com.example.smaro.ui.play.quickquiz

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

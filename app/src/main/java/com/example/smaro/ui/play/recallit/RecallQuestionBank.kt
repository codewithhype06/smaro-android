package com.example.smaro.ui.play.recallit

/**
 * Static recall question bank
 * Backend / AI baad me yahin se replace hoga
 */
object RecallQuestionBank {

    fun getQuestions(): List<RecallItem> {
        return listOf(
            RecallItem(
                id = 1,
                question = "Explain JVM in simple words",
                answer = "JVM runs Java bytecode and allows Java programs to run on any system."
            ),
            RecallItem(
                id = 2,
                question = "What is Jetpack Compose?",
                answer = "Jetpack Compose is Android’s modern UI toolkit for building UI using Kotlin."
            ),
            RecallItem(
                id = 3,
                question = "What is an Activity in Android?",
                answer = "An Activity represents a single screen with a user interface in Android."
            ),
            RecallItem(
                id = 4,
                question = "What does MVVM stand for?",
                answer = "MVVM stands for Model View ViewModel, an architecture pattern."
            ),
            RecallItem(
                id = 5,
                question = "What is State in Jetpack Compose?",
                answer = "State represents data that can change over time and trigger UI updates."
            )
        )
    }
}

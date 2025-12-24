package com.example.smaro.ui.play.matchtoearn

/**
 * Static question bank for Match To Earn game
 * Backend / AI later replaceable
 */
object MatchQuestionBank {

    fun getPairs(): List<MatchItem> {
        return listOf(
            MatchItem(
                id = 1,
                leftText = "JVM",
                rightText = "Runs Java bytecode on any system"
            ),
            MatchItem(
                id = 2,
                leftText = "Jetpack Compose",
                rightText = "Modern UI toolkit for Android"
            ),
            MatchItem(
                id = 3,
                leftText = "MVVM",
                rightText = "Architecture pattern for UI apps"
            ),
            MatchItem(
                id = 4,
                leftText = "Activity",
                rightText = "Represents a single screen in Android"
            ),
            MatchItem(
                id = 5,
                leftText = "State",
                rightText = "Holds data that drives UI updates"
            )
        )
    }
}

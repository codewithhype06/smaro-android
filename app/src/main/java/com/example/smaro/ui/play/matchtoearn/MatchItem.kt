package com.example.smaro.ui.play.matchtoearn

/**
 * Single matching pair item
 * left  -> concept
 * right -> definition
 */
data class MatchItem(
    val id: Int,
    val leftText: String,
    val rightText: String
)

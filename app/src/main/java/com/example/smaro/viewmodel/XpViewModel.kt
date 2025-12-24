package com.example.smaro.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class XpViewModel : ViewModel() {

    // 🔹 Total XP (lifetime)
    var totalXp by mutableStateOf(0)
        private set

    // 🔹 XP before last game
    var currentXp by mutableStateOf(0)
        private set

    // 🔹 XP earned in last game only
    var lastEarnedXp by mutableStateOf(0)
        private set

    var level by mutableStateOf(1)
        private set

    var streak by mutableStateOf(0)
        private set

    private var lastStreakDay: Int? = null

    /* ---------------- XP LOGIC ---------------- */

    fun addXp(amount: Int) {
        if (amount <= 0) return

        lastEarnedXp = amount
        currentXp = totalXp
        totalXp += amount

        calculateLevel()
    }

    private fun calculateLevel() {
        level = (totalXp / 100) + 1
    }

    /* ---------------- STREAK LOGIC ---------------- */

    fun incrementStreakIfNewDay() {
        val today = LocalDate.now().dayOfYear
        if (lastStreakDay == null || lastStreakDay != today) {
            streak += 1
            lastStreakDay = today
        }
    }

    fun resetStreak() {
        streak = 0
        lastStreakDay = null
    }
}

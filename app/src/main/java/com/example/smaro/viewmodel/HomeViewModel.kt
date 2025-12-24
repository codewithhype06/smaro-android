package com.example.smaro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smaro.data.FakeRepository

class HomeViewModel : ViewModel() {

    private val repository = FakeRepository()

    val xp: Int = repository.getXP()
    val streak: Int = repository.getStreak()
    val dailyRecall = repository.getDailyRecall()
    val conceptOfTheDay: String = repository.getConceptOfTheDay()
}

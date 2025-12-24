package com.example.smaro.model

data class FocusPlan(
    val id: String,
    val topic: String,
    val startDateMillis: Long,
    val endDateMillis: Long,
    val createdAtMillis: Long
)

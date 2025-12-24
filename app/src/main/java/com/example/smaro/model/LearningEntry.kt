package com.example.smaro.model

import java.util.UUID

/**
 * Core data model for a single daily learning entry.
 * Pure Kotlin model — no Android dependencies.
 */
data class LearningEntry(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val dateMillis: Long,
    val tags: List<String> = emptyList(),
    val createdAtMillis: Long = System.currentTimeMillis()
)

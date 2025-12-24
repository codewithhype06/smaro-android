package com.example.smaro.model

data class Capsule(
    val id: Long = System.currentTimeMillis(),
    val topic: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis()
)

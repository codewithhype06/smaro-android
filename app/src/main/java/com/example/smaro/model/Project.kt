package com.example.smaro.model

data class Project(
    val title: String,
    val description: String,
    val techStack: String,
    val repoLink: String = ""
)

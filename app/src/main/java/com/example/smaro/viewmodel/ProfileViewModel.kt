package com.example.smaro.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.smaro.model.*

class ProfileViewModel : ViewModel() {

    // 🔹 STATE LISTS (auto UI update)
    val skills = mutableStateListOf(
        Skill("Kotlin", "Intermediate"),
        Skill("Jetpack Compose", "Beginner")
    )

    val projects = mutableStateListOf(
        Project(
            title = "Smaro App",
            description = "Learning & career app",
            techStack = "Kotlin, Compose"
        )
    )

    val internships = mutableStateListOf(
        Internship(
            role = "Android Intern",
            company = "CodeCraft",
            duration = "2 Months",
            description = "Worked on UI & APIs"
        )
    )

    val certificates = mutableStateListOf(
        Certificate(
            title = "Android Development",
            platform = "Coursera",
            year = "2024"
        )
    )

    // 🔹 ADD FUNCTIONS
    fun addSkill(skill: Skill) {
        skills.add(skill)
    }

    fun addProject(project: Project) {
        projects.add(project)
    }

    fun addInternship(internship: Internship) {
        internships.add(internship)
    }

    fun addCertificate(certificate: Certificate) {
        certificates.add(certificate)
    }
}

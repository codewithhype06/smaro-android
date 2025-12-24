package com.example.smaro.data

import com.example.smaro.model.Skill
import com.example.smaro.model.Project
import com.example.smaro.model.Internship

class FakeRepository {

    fun getXP(): Int = 120

    fun getStreak(): Int = 5

    fun getDailyRecall(): DailyRecall {
        return DailyRecall(
            title = "Revise Operating Systems",
            description = "Process, Threads & Deadlock basics"
        )
    }

    fun getConceptOfTheDay(): String {
        return "Deadlock occurs when each process holds a resource and waits for others."
    }

    // 🧠 SKILLS DATA
    fun getSkills(): List<Skill> {
        return listOf(
            Skill("Kotlin", "Intermediate"),
            Skill("Jetpack Compose", "Beginner"),
            Skill("Data Structures", "Intermediate"),
            Skill("Git & GitHub", "Intermediate")
        )
    }

    // 💼 PROJECTS DATA
    fun getProjects(): List<Project> {
        return listOf(
            Project(
                title = "Smaro – Learning & Resume App",
                description = "Android app for learning, quizzes and instant resume building.",
                techStack = "Kotlin, Jetpack Compose"
            ),
            Project(
                title = "Quiz Master",
                description = "MCQ based quiz app with XP and score system.",
                techStack = "Kotlin, MVVM"
            )
        )
    }

    // 🏢 INTERNSHIPS DATA ✅
    fun getInternships(): List<Internship> {
        return listOf(
            Internship(
                role = "Android Developer Intern",
                company = "TechNova Solutions",
                duration = "Jan 2024 – Mar 2024",
                description = "Worked on Jetpack Compose UI and MVVM architecture."
            ),
            Internship(
                role = "Software Engineering Intern",
                company = "CodeCraft Labs",
                duration = "Jun 2023 – Aug 2023",
                description = "Built REST API integrations and improved app performance."
            )
        )
    }
}

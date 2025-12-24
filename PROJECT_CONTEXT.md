# SMARO – Android App Project Context

## Tech Stack
- Language: Kotlin
- UI: Jetpack Compose (Material 3)
- Architecture: MVVM
- State: ViewModel + MutableState
- Navigation:
    - Auth flow: NavGraph (Splash → Login → Main)
    - Main app: Bottom Navigation (Home, Learn, Play, Compete, Profile)

---

## App Structure

com.example.smaro
│
├── data
│   └── FakeRepository.kt
│
├── model
│   ├── Skill.kt
│   ├── Project.kt
│   ├── Internship.kt
│   └── Certificate.kt
│
├── navigation
│   └── NavGraph.kt
│
├── ui
│   ├── home
│   │   └── HomeScreen.kt
│   │
│   ├── learn
│   │   └── LearnScreen.kt (placeholder)
│   │
│   ├── play
│   │   └── PlayScreen.kt (placeholder)
│   │
│   ├── compete
│   │   └── CompeteScreen.kt (placeholder)
│   │
│   ├── profile
│   │   ├── ProfileScreen.kt
│   │   ├── component
│   │   │   └── ExpandableSection.kt
│   │   └── dialogs
│   │       ├── AddSkillDialog.kt
│   │       ├── AddProjectDialog.kt
│   │       ├── AddInternshipDialog.kt
│   │       └── AddCertificateDialog.kt
│   │
│   ├── resume
│   │   ├── SelectRoleScreen.kt
│   │   └── ResumePreviewScreen.kt
│   │
│   └── main
│       └── MainScreen.kt
│
└── viewmodel
├── HomeViewModel.kt
└── ProfileViewModel.kt

---

## Key Features Implemented

### Home Screen
- XP + Streak
- Daily Recall
- Concept of the Day
- Quick Actions (Learn / Play / Revise)

### Profile Screen
- Scrollable layout (LazyColumn)
- Expandable sections:
    - Skills
    - Projects
    - Internships
    - Certificates
- Add flows for all sections (Dialogs working)
- Resume Builder entry point

### Resume Builder (In Progress)
- Select Job Role
- Auto-generate resume from Profile data
- Resume Preview screen

---

## Current Status
- App runs without crash
- Home + Profile stable
- All add flows working
- Learn / Play / Compete screens are placeholders
- Resume Builder UI started

---

## Notes for AI / Developer
- Do NOT change working Home/Profile logic unless explicitly asked
- Maintain Material3 + Compose conventions
- Avoid deprecated APIs
- Crash-proof first, UI polish later

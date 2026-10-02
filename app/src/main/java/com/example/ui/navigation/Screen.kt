package com.example.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Chapters : Screen("chapters")
    data object LessonDetail : Screen("lesson_detail/{chapterId}") {
        fun createRoute(chapterId: Int) = "lesson_detail/$chapterId"
    }
    data object AiTutor : Screen("ai_tutor")
    data object Profile : Screen("profile")
    data object Troubleshooting : Screen("troubleshooting")
}

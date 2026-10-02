package com.example.data.model

enum class DifficultyLevel(
    val titleKhmer: String,
    val titleEnglish: String
) {
    BEGINNER("មូលដ្ឋាន", "Beginner"),
    INTERMEDIATE("មធ្យម", "Intermediate"),
    ADVANCED("កម្រិតខ្ពស់", "Advanced")
}

data class Chapter(
    val id: Int,
    val track: ComputerTrack,
    val chapterNumber: Int,
    val titleKhmer: String,
    val titleEnglish: String,
    val summaryKhmer: String,
    val summaryEnglish: String,
    val difficulty: DifficultyLevel = DifficultyLevel.BEGINNER,
    val estimatedMinutes: Int = 30,
    val keyConcepts: List<String> = emptyList(),
    val lessonsCount: Int = 3,
    val iconName: String = "computer",
    val isBookmarked: Boolean = false,
    val isCompleted: Boolean = false,
    val quizQuestions: List<QuizQuestion> = emptyList()
)

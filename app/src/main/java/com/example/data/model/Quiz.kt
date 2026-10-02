package com.example.data.model

data class QuizQuestion(
    val id: String,
    val questionKhmer: String,
    val questionEnglish: String,
    val optionsKhmer: List<String>,
    val optionsEnglish: List<String>,
    val correctIndex: Int,
    val explanationKhmer: String,
    val explanationEnglish: String
)

data class QuizResult(
    val chapterId: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val scorePercentage: Int,
    val passed: Boolean
)

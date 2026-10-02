package com.example.data.model

data class LessonSection(
    val titleKhmer: String,
    val titleEnglish: String,
    val contentKhmer: String,
    val contentEnglish: String,
    val codeSnippet: String? = null,
    val codeLanguage: String? = null,
    val calloutType: CalloutType = CalloutType.INFO,
    val calloutTextKhmer: String? = null,
    val calloutTextEnglish: String? = null
)

enum class CalloutType {
    INFO,
    WARNING,
    PRO_TIP,
    HARDWARE_SPEC
}

data class Lesson(
    val id: String,
    val chapterId: Int,
    val lessonNumber: Int,
    val titleKhmer: String,
    val titleEnglish: String,
    val durationMinutes: Int = 15,
    val overviewKhmer: String,
    val overviewEnglish: String,
    val sections: List<LessonSection>,
    val practicalCode: String? = null,
    val codeLanguage: String? = null,
    val keyTakeawaysKhmer: List<String> = emptyList(),
    val keyTakeawaysEnglish: List<String> = emptyList()
)

package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey
    val chapterId: Int,
    val isCompleted: Boolean = false,
    val completedLessonsCount: Int = 0,
    val quizScore: Int = 0,
    val lastStudiedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val chapterId: Int,
    val title: String,
    val trackId: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "student_notes")
data class StudentNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterId: Int,
    val lessonId: String,
    val noteContent: String,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sender: String, // "user" or "tutor"
    val messageText: String,
    val chapterContext: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

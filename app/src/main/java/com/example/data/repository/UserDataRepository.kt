package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.BookmarkEntity
import com.example.data.local.ChatMessageEntity
import com.example.data.local.StudentNoteEntity
import com.example.data.local.UserProgressEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataRepository(private val database: AppDatabase) {

    private val userDao = database.userDao()

    val allProgress: Flow<List<UserProgressEntity>> = userDao.getAllProgress()
    val allBookmarks: Flow<List<BookmarkEntity>> = userDao.getAllBookmarks()
    val allNotes: Flow<List<StudentNoteEntity>> = userDao.getAllNotes()
    val chatMessages: Flow<List<ChatMessageEntity>> = userDao.getChatMessages()

    fun isChapterBookmarked(chapterId: Int): Flow<Boolean> =
        userDao.isBookmarked(chapterId)

    fun getChapterProgress(chapterId: Int): Flow<UserProgressEntity?> =
        userDao.getProgressForChapter(chapterId)

    fun getNotesForLesson(lessonId: String): Flow<List<StudentNoteEntity>> =
        userDao.getNotesForLesson(lessonId)

    suspend fun toggleBookmark(chapterId: Int, title: String, trackId: String, currentlyBookmarked: Boolean) {
        if (currentlyBookmarked) {
            userDao.removeBookmark(chapterId)
        } else {
            userDao.addBookmark(
                BookmarkEntity(
                    chapterId = chapterId,
                    title = title,
                    trackId = trackId
                )
            )
        }
    }

    suspend fun markChapterCompleted(chapterId: Int, score: Int = 100) {
        userDao.saveProgress(
            UserProgressEntity(
                chapterId = chapterId,
                isCompleted = true,
                completedLessonsCount = 3,
                quizScore = score,
                lastStudiedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun saveNote(chapterId: Int, lessonId: String, content: String) {
        if (content.isNotBlank()) {
            userDao.saveNote(
                StudentNoteEntity(
                    chapterId = chapterId,
                    lessonId = lessonId,
                    noteContent = content
                )
            )
        }
    }

    suspend fun deleteNote(id: Long) {
        userDao.deleteNote(id)
    }

    suspend fun saveChatMessage(sender: String, messageText: String, chapterContext: String? = null) {
        userDao.insertChatMessage(
            ChatMessageEntity(
                sender = sender,
                messageText = messageText,
                chapterContext = chapterContext
            )
        )
    }

    suspend fun clearChat() {
        userDao.clearChatMessages()
    }
}

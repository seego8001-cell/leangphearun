package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // Progress
    @Query("SELECT * FROM user_progress")
    fun getAllProgress(): Flow<List<UserProgressEntity>>

    @Query("SELECT * FROM user_progress WHERE chapterId = :chapterId LIMIT 1")
    fun getProgressForChapter(chapterId: Int): Flow<UserProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(progress: UserProgressEntity)

    // Bookmarks
    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE chapterId = :chapterId)")
    fun isBookmarked(chapterId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE chapterId = :chapterId")
    suspend fun removeBookmark(chapterId: Int)

    // Notes
    @Query("SELECT * FROM student_notes WHERE lessonId = :lessonId ORDER BY updatedAt DESC")
    fun getNotesForLesson(lessonId: String): Flow<List<StudentNoteEntity>>

    @Query("SELECT * FROM student_notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<StudentNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: StudentNoteEntity)

    @Query("DELETE FROM student_notes WHERE id = :id")
    suspend fun deleteNote(id: Long)

    // Chat
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getChatMessages(): Flow<List<ChatMessageEntity>>

    @Insert
    suspend fun insertChatMessage(message: ChatMessageEntity)

    @Query("DELETE FROM chat_messages")
    suspend fun clearChatMessages()
}

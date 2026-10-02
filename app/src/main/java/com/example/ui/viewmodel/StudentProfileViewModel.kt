package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BookmarkEntity
import com.example.data.local.StudentNoteEntity
import com.example.data.local.UserProgressEntity
import com.example.data.model.ComputerTrack
import com.example.data.repository.CurriculumCatalog
import com.example.data.repository.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class StudentProfileUiState(
    val completedChaptersCount: Int = 0,
    val totalChapters: Int = 120,
    val completionPercentage: Int = 0,
    val bookmarkedChapters: List<BookmarkEntity> = emptyList(),
    val notes: List<StudentNoteEntity> = emptyList(),
    val trackProgressMap: Map<ComputerTrack, Int> = emptyMap(),
    val studentLevel: String = "អ្នកចាប់ផ្តើម (Beginner)",
    val studentPoints: Int = 0
)

class StudentProfileViewModel(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val uiState: StateFlow<StudentProfileUiState> = combine(
        userDataRepository.allProgress,
        userDataRepository.allBookmarks,
        userDataRepository.allNotes
    ) { progressList, bookmarks, notes ->
        val completed = progressList.filter { it.isCompleted }
        val count = completed.size
        val total = 120
        val percentage = (count * 100) / total

        // Track breakdown
        val trackMap = mutableMapOf<ComputerTrack, Int>()
        ComputerTrack.entries.forEach { track ->
            val completedInTrack = completed.count { prog ->
                val ch = CurriculumCatalog.getChapterById(prog.chapterId)
                ch?.track == track
            }
            trackMap[track] = completedInTrack
        }

        val level = when {
            count >= 100 -> "វិស្វករកុំព្យូទ័រជាន់ខ្ពស់ (Expert Architect)"
            count >= 60 -> "អ្នកជំនាញកុំព្យូទ័រ (Advanced Specialist)"
            count >= 20 -> "និស្សិតកម្រិតមធ្យម (Intermediate Scholar)"
            count >= 5 -> "អ្នកសិក្សាសកម្ម (Active Learner)"
            else -> "អ្នកចាប់ផ្តើម (Beginner Explorer)"
        }

        val points = count * 50 + notes.size * 10

        StudentProfileUiState(
            completedChaptersCount = count,
            totalChapters = total,
            completionPercentage = percentage,
            bookmarkedChapters = bookmarks,
            notes = notes,
            trackProgressMap = trackMap,
            studentLevel = level,
            studentPoints = points
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        StudentProfileUiState()
    )

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            userDataRepository.deleteNote(id)
        }
    }
}

package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Chapter
import com.example.data.model.ComputerTrack
import com.example.data.model.DifficultyLevel
import com.example.data.repository.CurriculumCatalog
import com.example.data.repository.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CurriculumUiState(
    val selectedTrack: ComputerTrack? = null,
    val searchQuery: String = "",
    val selectedDifficulty: DifficultyLevel? = null,
    val onlyBookmarked: Boolean = false,
    val onlyCompleted: Boolean = false,
    val isKhmerLanguage: Boolean = true,
    val allChapters: List<Chapter> = emptyList(),
    val filteredChapters: List<Chapter> = emptyList(),
    val completedChapterIds: Set<Int> = emptySet(),
    val bookmarkedChapterIds: Set<Int> = emptySet()
)

class CurriculumViewModel(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _selectedTrack = MutableStateFlow<ComputerTrack?>(null)
    private val _searchQuery = MutableStateFlow("")
    private val _selectedDifficulty = MutableStateFlow<DifficultyLevel?>(null)
    private val _onlyBookmarked = MutableStateFlow(false)
    private val _onlyCompleted = MutableStateFlow(false)
    private val _isKhmerLanguage = MutableStateFlow(true)

    val uiState: StateFlow<CurriculumUiState> = combine(
        combine(_selectedTrack, _searchQuery, _selectedDifficulty, _onlyBookmarked, _onlyCompleted) { track, q, diff, bookmarked, completed ->
            FilterTuple(track, q, diff, bookmarked, completed)
        },
        _isKhmerLanguage,
        userDataRepository.allProgress,
        userDataRepository.allBookmarks
    ) { filter, isKhmer, progressList, bookmarkList ->
        val completedIds = progressList.filter { it.isCompleted }.map { it.chapterId }.toSet()
        val bookmarkedIds = bookmarkList.map { it.chapterId }.toSet()

        val all = CurriculumCatalog.allChapters.map { ch ->
            ch.copy(
                isCompleted = completedIds.contains(ch.id),
                isBookmarked = bookmarkedIds.contains(ch.id)
            )
        }

        val filtered = all.filter { ch ->
            val matchesTrack = filter.track == null || ch.track == filter.track
            val matchesDiff = filter.diff == null || ch.difficulty == filter.diff
            val matchesQuery = filter.query.isBlank() ||
                    ch.titleKhmer.contains(filter.query, ignoreCase = true) ||
                    ch.titleEnglish.contains(filter.query, ignoreCase = true) ||
                    ch.summaryKhmer.contains(filter.query, ignoreCase = true) ||
                    ch.summaryEnglish.contains(filter.query, ignoreCase = true) ||
                    ch.chapterNumber.toString() == filter.query.trim()
            val matchesBookmarked = !filter.onlyBookmarked || bookmarkedIds.contains(ch.id)
            val matchesCompleted = !filter.onlyCompleted || completedIds.contains(ch.id)

            matchesTrack && matchesDiff && matchesQuery && matchesBookmarked && matchesCompleted
        }

        CurriculumUiState(
            selectedTrack = filter.track,
            searchQuery = filter.query,
            selectedDifficulty = filter.diff,
            onlyBookmarked = filter.onlyBookmarked,
            onlyCompleted = filter.onlyCompleted,
            isKhmerLanguage = isKhmer,
            allChapters = all,
            filteredChapters = filtered,
            completedChapterIds = completedIds,
            bookmarkedChapterIds = bookmarkedIds
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CurriculumUiState()
    )

    fun selectTrack(track: ComputerTrack?) {
        _selectedTrack.value = track
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setDifficulty(diff: DifficultyLevel?) {
        _selectedDifficulty.value = diff
    }

    fun toggleOnlyBookmarked() {
        _onlyBookmarked.value = !_onlyBookmarked.value
    }

    fun toggleOnlyCompleted() {
        _onlyCompleted.value = !_onlyCompleted.value
    }

    fun toggleLanguage() {
        _isKhmerLanguage.value = !_isKhmerLanguage.value
    }

    fun toggleBookmark(chapter: Chapter) {
        viewModelScope.launch {
            userDataRepository.toggleBookmark(
                chapterId = chapter.id,
                title = if (_isKhmerLanguage.value) chapter.titleKhmer else chapter.titleEnglish,
                trackId = chapter.track.id,
                currentlyBookmarked = chapter.isBookmarked
            )
        }
    }
}

private data class FilterTuple(
    val track: ComputerTrack?,
    val query: String,
    val diff: DifficultyLevel?,
    val onlyBookmarked: Boolean,
    val onlyCompleted: Boolean
)

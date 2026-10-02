package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.StudentNoteEntity
import com.example.data.model.Chapter
import com.example.data.model.Lesson
import com.example.data.model.QuizQuestion
import com.example.data.repository.CurriculumCatalog
import com.example.data.repository.LessonContentProvider
import com.example.data.repository.QuizDataProvider
import com.example.data.repository.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class LessonUiState(
    val chapter: Chapter? = null,
    val lessons: List<Lesson> = emptyList(),
    val currentLessonIndex: Int = 0,
    val currentLesson: Lesson? = null,
    val notes: List<StudentNoteEntity> = emptyList(),
    val newNoteText: String = "",
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val quizSelectedAnswers: Map<Int, Int> = emptyMap(),
    val isQuizSubmitted: Boolean = false,
    val quizScore: Int = 0,
    val isChapterCompleted: Boolean = false,
    val isBookmarked: Boolean = false
)

class LessonViewModel(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LessonUiState())
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    fun loadChapter(chapterId: Int) {
        val ch = CurriculumCatalog.getChapterById(chapterId) ?: return
        val lessons = LessonContentProvider.getLessonsForChapter(chapterId)
        val quiz = QuizDataProvider.getQuizForChapter(chapterId)

        _uiState.value = _uiState.value.copy(
            chapter = ch,
            lessons = lessons,
            currentLessonIndex = 0,
            currentLesson = lessons.firstOrNull(),
            quizQuestions = quiz,
            quizSelectedAnswers = emptyMap(),
            isQuizSubmitted = false,
            quizScore = 0
        )

        loadNotesForCurrentLesson()
        observeChapterStatus(chapterId)
    }

    private fun observeChapterStatus(chapterId: Int) {
        viewModelScope.launch {
            userDataRepository.getChapterProgress(chapterId).collect { progress ->
                _uiState.value = _uiState.value.copy(
                    isChapterCompleted = progress?.isCompleted == true
                )
            }
        }
        viewModelScope.launch {
            userDataRepository.isChapterBookmarked(chapterId).collect { isBookmarked ->
                _uiState.value = _uiState.value.copy(
                    isBookmarked = isBookmarked
                )
            }
        }
    }

    fun selectLesson(index: Int) {
        val lessons = _uiState.value.lessons
        if (index in lessons.indices) {
            _uiState.value = _uiState.value.copy(
                currentLessonIndex = index,
                currentLesson = lessons[index]
            )
            loadNotesForCurrentLesson()
        }
    }

    private fun loadNotesForCurrentLesson() {
        val current = _uiState.value.currentLesson ?: return
        viewModelScope.launch {
            userDataRepository.getNotesForLesson(current.id).collect { notesList ->
                _uiState.value = _uiState.value.copy(notes = notesList)
            }
        }
    }

    fun updateNewNoteText(text: String) {
        _uiState.value = _uiState.value.copy(newNoteText = text)
    }

    fun saveCurrentNote() {
        val currentLesson = _uiState.value.currentLesson ?: return
        val chapterId = _uiState.value.chapter?.id ?: return
        val content = _uiState.value.newNoteText.trim()
        if (content.isNotBlank()) {
            viewModelScope.launch {
                userDataRepository.saveNote(chapterId, currentLesson.id, content)
                _uiState.value = _uiState.value.copy(newNoteText = "")
            }
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            userDataRepository.deleteNote(id)
        }
    }

    fun selectQuizAnswer(questionIndex: Int, optionIndex: Int) {
        if (_uiState.value.isQuizSubmitted) return
        val currentAnswers = _uiState.value.quizSelectedAnswers.toMutableMap()
        currentAnswers[questionIndex] = optionIndex
        _uiState.value = _uiState.value.copy(quizSelectedAnswers = currentAnswers)
    }

    fun submitQuiz() {
        val questions = _uiState.value.quizQuestions
        if (questions.isEmpty()) return

        var correct = 0
        questions.forEachIndexed { i, q ->
            val userSelected = _uiState.value.quizSelectedAnswers[i]
            if (userSelected == q.correctIndex) {
                correct++
            }
        }

        val percentage = (correct * 100) / questions.size
        _uiState.value = _uiState.value.copy(
            isQuizSubmitted = true,
            quizScore = percentage
        )

        // If score >= 60%, mark chapter as completed
        if (percentage >= 60) {
            _uiState.value.chapter?.id?.let { chId ->
                viewModelScope.launch {
                    userDataRepository.markChapterCompleted(chId, percentage)
                }
            }
        }
    }

    fun markCompleted() {
        _uiState.value.chapter?.id?.let { chId ->
            viewModelScope.launch {
                userDataRepository.markChapterCompleted(chId, 100)
            }
        }
    }

    fun toggleBookmark() {
        val ch = _uiState.value.chapter ?: return
        viewModelScope.launch {
            userDataRepository.toggleBookmark(
                chapterId = ch.id,
                title = ch.titleKhmer,
                trackId = ch.track.id,
                currentlyBookmarked = _uiState.value.isBookmarked
            )
        }
    }
}

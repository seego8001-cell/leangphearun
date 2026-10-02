package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.ChatMessageEntity
import com.example.data.remote.GeminiTutorService
import com.example.data.repository.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AiTutorUiState(
    val messages: List<ChatMessageEntity> = emptyList(),
    val isThinking: Boolean = false,
    val currentInput: String = "",
    val activeChapterContext: String? = null,
    val suggestedPrompts: List<String> = listOf(
        "ពន្យល់ពីរបៀបដំណើរការរបស់ CPU",
        "ភាពខុសគ្នារវាង DDR4 និង DDR5 RAM",
        "របៀបដោះស្រាយបញ្ហាអេក្រង់ខៀវ BSOD",
        "តើ OSI Model ទាំង ៧ ជាន់មានអ្វីខ្លះ?",
        "ពន្យល់ពី TCP 3-Way Handshake",
        "ណែនាំផែនការសិក្សាដើម្បីក្លាយជា Software Engineer"
    )
)

class AiTutorViewModel(
    private val userDataRepository: UserDataRepository,
    private val tutorService: GeminiTutorService = GeminiTutorService()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiTutorUiState())
    val uiState: StateFlow<AiTutorUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userDataRepository.chatMessages.collect { savedMessages ->
                if (savedMessages.isEmpty()) {
                    // Send initial welcome message
                    val welcome = ChatMessageEntity(
                        sender = "tutor",
                        messageText = "សួស្តីប្អូនសិស្សានុសិស្ស! ខ្ញុំជា 'លោកគ្រូជំនួយ Computer Tutor'។ ខ្ញុំត្រៀមខ្លួនរួចរាល់ដើម្បីជួយបង្រៀន ពន្យល់មេរៀន ដោះស្រាយបញ្ហាកូដ ឬជួយដោះស្រាយបញ្ហាកុំព្យូទ័រគ្រប់ពេល។ តើប្អូនចង់រៀន ឬចង់សួរអ្វីនៅថ្ងៃនេះ?"
                    )
                    _uiState.value = _uiState.value.copy(messages = listOf(welcome))
                } else {
                    _uiState.value = _uiState.value.copy(messages = savedMessages)
                }
            }
        }
    }

    fun setChapterContext(chapterTitle: String) {
        _uiState.value = _uiState.value.copy(
            activeChapterContext = chapterTitle
        )
    }

    fun updateInput(input: String) {
        _uiState.value = _uiState.value.copy(currentInput = input)
    }

    fun sendMessage(customPrompt: String? = null) {
        val query = (customPrompt ?: _uiState.value.currentInput).trim()
        if (query.isBlank() || _uiState.value.isThinking) return

        val context = _uiState.value.activeChapterContext

        viewModelScope.launch {
            // Save user message to DB
            userDataRepository.saveChatMessage(
                sender = "user",
                messageText = query,
                chapterContext = context
            )
            _uiState.value = _uiState.value.copy(
                currentInput = "",
                isThinking = true
            )

            // Call Gemini Tutor
            val responseResult = tutorService.askTutor(
                userMessage = query,
                lessonContext = context
            )

            val tutorAnswer = responseResult.getOrElse {
                "សូមអភ័យទោស ប្អូនសិស្ស! ប្រព័ន្ធមានបញ្ហាតភ្ជាប់បណ្តាញបន្តិច។ សូមសាកល្បងសួរម្តងទៀត!"
            }

            // Save tutor message to DB
            userDataRepository.saveChatMessage(
                sender = "tutor",
                messageText = tutorAnswer,
                chapterContext = context
            )

            _uiState.value = _uiState.value.copy(isThinking = false)
        }
    }

    fun clearChatHistory() {
        viewModelScope.launch {
            userDataRepository.clearChat()
            val welcome = ChatMessageEntity(
                sender = "tutor",
                messageText = "ប្រវត្តិសន្ទនាត្រូវបានសម្អាតរួចរាល់។ តើប្អូនចង់ឱ្យលោកគ្រូជួយពន្យល់មេរៀនណាថ្មី?"
            )
            _uiState.value = _uiState.value.copy(messages = listOf(welcome))
        }
    }
}

package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DifficultyBadge
import com.example.ui.components.InteractiveCodeBlock
import com.example.ui.components.LessonCalloutBox
import com.example.ui.components.TrackBadge
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.EmeraldAccent
import com.example.ui.viewmodel.LessonUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonDetailScreen(
    uiState: LessonUiState,
    isKhmer: Boolean,
    onBackClick: () -> Unit,
    onSelectLesson: (Int) -> Unit,
    onNewNoteChange: (String) -> Unit,
    onSaveNote: () -> Unit,
    onDeleteNote: (Long) -> Unit,
    onSelectQuizAnswer: (Int, Int) -> Unit,
    onSubmitQuiz: () -> Unit,
    onMarkCompleted: () -> Unit,
    onToggleBookmark: () -> Unit,
    onAskAiAboutLesson: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val chapter = uiState.chapter ?: return
    var selectedTab by remember { mutableIntStateOf(0) } // 0..2 = Lessons, 3 = Quiz

    Scaffold(
        modifier = modifier.fillMaxSize().testTag("lesson_detail_screen"),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "ជំពូកទី #${chapter.chapterNumber}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isKhmer) chapter.titleKhmer else chapter.titleEnglish,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick, modifier = Modifier.testTag("lesson_back_button")) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onToggleBookmark, modifier = Modifier.testTag("detail_bookmark_btn")) {
                        Icon(
                            imageVector = if (uiState.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (uiState.isBookmarked) CyanPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 6.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            val contextName = if (isKhmer) chapter.titleKhmer else chapter.titleEnglish
                            onAskAiAboutLesson(contextName)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = CyanPrimary
                        ),
                        modifier = Modifier.weight(1f).testTag("ask_ai_from_lesson_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isKhmer) "សួរគ្រូ AI" else "Ask AI Tutor", fontSize = 12.sp)
                    }

                    Button(
                        onClick = onMarkCompleted,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.isChapterCompleted) EmeraldAccent else CyanPrimary,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.weight(1.2f).testTag("mark_complete_btn"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (uiState.isChapterCompleted) "បានបញ្ចប់ (Done)" else "បញ្ចប់ជំពូកនេះ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Chapter Header Info
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            TrackBadge(track = chapter.track, isKhmer = isKhmer)
                            Spacer(modifier = Modifier.width(8.dp))
                            DifficultyBadge(difficulty = chapter.difficulty, isKhmer = isKhmer)
                        }
                        if (uiState.isChapterCompleted) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = EmeraldAccent,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isKhmer) "ជោគជ័យ" else "Completed",
                                    fontSize = 12.sp,
                                    color = EmeraldAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isKhmer) chapter.titleKhmer else chapter.titleEnglish,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isKhmer) chapter.titleEnglish else chapter.titleKhmer,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Tabs for Lessons + Quiz
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = CyanPrimary,
                edgePadding = 16.dp
            ) {
                uiState.lessons.forEachIndexed { i, les ->
                    Tab(
                        selected = selectedTab == i,
                        onClick = {
                            selectedTab = i
                            onSelectLesson(i)
                        },
                        text = {
                            Text(
                                text = "មេរៀន ${i + 1}",
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == i) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }

                Tab(
                    selected = selectedTab == uiState.lessons.size,
                    onClick = { selectedTab = uiState.lessons.size },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Quiz តេស្ត",
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == uiState.lessons.size) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                )
            }

            // Tab Content
            if (selectedTab < uiState.lessons.size) {
                val currentLesson = uiState.currentLesson
                if (currentLesson != null) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Lesson Title & Overview
                        item {
                            Text(
                                text = if (isKhmer) currentLesson.titleKhmer else currentLesson.titleEnglish,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CyanPrimary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (isKhmer) currentLesson.overviewKhmer else currentLesson.overviewEnglish,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 20.sp
                            )
                        }

                        // Sections
                        items(currentLesson.sections) { sec ->
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = if (isKhmer) sec.titleKhmer else sec.titleEnglish,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = if (isKhmer) sec.contentKhmer else sec.contentEnglish,
                                        style = MaterialTheme.typography.bodyMedium,
                                        lineHeight = 22.sp
                                    )

                                    if (sec.calloutTextKhmer != null && sec.calloutTextEnglish != null) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                        LessonCalloutBox(
                                            type = sec.calloutType,
                                            textKhmer = sec.calloutTextKhmer,
                                            textEnglish = sec.calloutTextEnglish,
                                            isKhmer = isKhmer
                                        )
                                    }
                                }
                            }
                        }

                        // Code / Terminal Snippet
                        if (currentLesson.practicalCode != null) {
                            item {
                                Column {
                                    Text(
                                        text = if (isKhmer) "ការអនុវត្តកូដជាក់ស្តែង (Hands-on Code / Commands)" else "Practical Hands-on Code / Commands",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    InteractiveCodeBlock(
                                        code = currentLesson.practicalCode,
                                        language = currentLesson.codeLanguage
                                    )
                                }
                            }
                        }

                        // Key Takeaways
                        if (currentLesson.keyTakeawaysKhmer.isNotEmpty()) {
                            item {
                                Card(
                                    shape = RoundedCornerShape(14.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF132238))
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text(
                                            text = if (isKhmer) "📌 ចំណុចគន្លឹះសំខាន់ៗ (Key Takeaways)" else "📌 Key Takeaways",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = CyanPrimary
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        val list = if (isKhmer) currentLesson.keyTakeawaysKhmer else currentLesson.keyTakeawaysEnglish
                                        list.forEach { item ->
                                            Row(modifier = Modifier.padding(vertical = 3.dp)) {
                                                Text("• ", color = CyanPrimary, fontWeight = FontWeight.Bold)
                                                Text(item, style = MaterialTheme.typography.bodySmall, color = Color(0xFFE2E8F0))
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Student Personal Notes
                        item {
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.EditNote, contentDescription = null, tint = CyanPrimary)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (isKhmer) "កត់ត្រាផ្ទាល់ខ្លួន (Personal Notes)" else "Student Notes",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        OutlinedTextField(
                                            value = uiState.newNoteText,
                                            onValueChange = onNewNoteChange,
                                            placeholder = { Text(if (isKhmer) "សរសេរចំណាំទុកក្នុងមេរៀននេះ..." else "Write a study note...", fontSize = 12.sp) },
                                            modifier = Modifier.weight(1f).testTag("note_input_field"),
                                            shape = RoundedCornerShape(10.dp),
                                            singleLine = false,
                                            maxLines = 3
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        IconButton(
                                            onClick = onSaveNote,
                                            modifier = Modifier.background(CyanPrimary, CircleShape).testTag("save_note_btn")
                                        ) {
                                            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Save Note", tint = Color.Black)
                                        }
                                    }

                                    if (uiState.notes.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(12.dp))
                                        uiState.notes.forEach { note ->
                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = MaterialTheme.colorScheme.background,
                                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(10.dp),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(note.noteContent, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                                                    IconButton(onClick = { onDeleteNote(note.id) }, modifier = Modifier.size(24.dp)) {
                                                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFEF4444), modifier = Modifier.size(16.dp))
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Quiz Screen
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = if (isKhmer) "តេស្តវាស់ស្ទង់ចំណេះដឹងជំពូកទី #${chapter.chapterNumber}" else "Chapter Assessment Quiz",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CyanPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isKhmer) "ឆ្លើយសំណួរឱ្យបាន ៦០% ឡើងទៅដើម្បីទទួលបានការបញ្ជាក់ជោគជ័យ!" else "Score 60%+ to successfully complete this chapter!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(uiState.quizQuestions.indices.toList()) { qIndex ->
                        val question = uiState.quizQuestions[qIndex]
                        val userSelected = uiState.quizSelectedAnswers[qIndex]

                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "សំណួរទី ${qIndex + 1}: ${if (isKhmer) question.questionKhmer else question.questionEnglish}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                val options = if (isKhmer) question.optionsKhmer else question.optionsEnglish
                                options.forEachIndexed { optIndex, optText ->
                                    val isSelected = userSelected == optIndex
                                    val isCorrect = optIndex == question.correctIndex

                                    val (bgColor, borderColor) = when {
                                        !uiState.isQuizSubmitted && isSelected -> Pair(CyanPrimary.copy(alpha = 0.2f), CyanPrimary)
                                        uiState.isQuizSubmitted && isCorrect -> Pair(EmeraldAccent.copy(alpha = 0.25f), EmeraldAccent)
                                        uiState.isQuizSubmitted && isSelected && !isCorrect -> Pair(Color(0xFFEF4444).copy(alpha = 0.25f), Color(0xFFEF4444))
                                        else -> Pair(MaterialTheme.colorScheme.background, Color.Transparent)
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = bgColor,
                                        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable(enabled = !uiState.isQuizSubmitted) {
                                                onSelectQuizAnswer(qIndex, optIndex)
                                            }
                                            .testTag("quiz_q${qIndex}_opt$optIndex")
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clip(CircleShape)
                                                    .background(if (isSelected) CyanPrimary else MaterialTheme.colorScheme.outlineVariant),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "${'A' + optIndex}",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(
                                                text = optText,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }

                                if (uiState.isQuizSubmitted) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "💡 ពន្យល់: " + if (isKhmer) question.explanationKhmer else question.explanationEnglish,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = EmeraldAccent
                                    )
                                }
                            }
                        }
                    }

                    item {
                        if (!uiState.isQuizSubmitted) {
                            Button(
                                onClick = onSubmitQuiz,
                                modifier = Modifier.fillMaxWidth().height(48.dp).testTag("submit_quiz_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = CyanPrimary, contentColor = Color.Black),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(if (isKhmer) "ពិនិត្យចម្លើយ (Submit Quiz)" else "Submit Quiz", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF132338))
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "ពិន្ទុរបស់អ្នក: ${uiState.quizScore}%",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = if (uiState.quizScore >= 60) EmeraldAccent else Color(0xFFEF4444)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = if (uiState.quizScore >= 60) "🎉 អបអរសាទរ! ប្អូនបានឆ្លងផុតជំពូកនេះជោគជ័យ!" else "សូមព្យាយាមឡើងវិញដើម្បីទទួលបានពិន្ទុលើស ៦០%!",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

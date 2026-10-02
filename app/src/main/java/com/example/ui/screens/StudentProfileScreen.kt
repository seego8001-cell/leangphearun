package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ComputerTrack
import com.example.ui.components.StatCard
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.EmeraldAccent
import com.example.ui.viewmodel.StudentProfileUiState

@Composable
fun StudentProfileScreen(
    uiState: StudentProfileUiState,
    isKhmer: Boolean,
    onNavigateToChapter: (Int) -> Unit,
    onDeleteNote: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("student_profile_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // 1. Profile Header
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF132338)),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyanPrimary.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = CyanPrimary.copy(alpha = 0.2f),
                        modifier = Modifier.size(60.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = null,
                                tint = CyanPrimary,
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = if (isKhmer) "គណនីនិស្សិតកុំព្យូទ័រ" else "Student Academy Profile",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = uiState.studentLevel,
                            style = MaterialTheme.typography.bodySmall,
                            color = CyanPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "ពិន្ទុចំណេះដឹង: ${uiState.studentPoints} XP",
                            style = MaterialTheme.typography.labelSmall,
                            color = AmberAccent
                        )
                    }
                }
            }
        }

        // 2. Stats Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = if (isKhmer) "ជំពូកបានរៀន" else "Learned",
                    value = "${uiState.completedChaptersCount}/120",
                    subtitle = "${uiState.completionPercentage}% សម្រេចបាន",
                    iconColor = EmeraldAccent,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = if (isKhmer) "បានចំណាំ" else "Bookmarks",
                    value = "${uiState.bookmarkedChapters.size}",
                    subtitle = if (isKhmer) "ជំពូកចូលចិត្ត" else "Saved Chapters",
                    iconColor = CyanPrimary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = if (isKhmer) "កំណត់ត្រា" else "Notes",
                    value = "${uiState.notes.size}",
                    subtitle = if (isKhmer) "កត់ត្រាផ្ទាល់ខ្លួន" else "Study Notes",
                    iconColor = AmberAccent,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 3. Track Mastery Progress
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isKhmer) "វឌ្ឍនភាពតាមជំនាញទាំង ១០ (Track Progress)" else "Track Progress Breakdown",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    ComputerTrack.entries.forEach { track ->
                        val completedInTrack = uiState.trackProgressMap[track] ?: 0
                        val progressFloat = completedInTrack.toFloat() / track.totalChapters.toFloat()

                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (isKhmer) track.titleKhmer else track.titleEnglish,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "$completedInTrack/${track.totalChapters}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = CyanPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { progressFloat },
                                modifier = Modifier.fillMaxWidth().height(6.dp),
                                color = CyanPrimary,
                                trackColor = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }
            }
        }

        // 4. Bookmarked Chapters List
        if (uiState.bookmarkedChapters.isNotEmpty()) {
            item {
                Text(
                    text = if (isKhmer) "ជំពូកដែលបានចំណាំទុក (Bookmarked Chapters)" else "Saved Bookmarks",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(uiState.bookmarkedChapters) { b ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToChapter(b.chapterId) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, tint = CyanPrimary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ជំពូកទី #${b.chapterId}: ${b.title}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "ជំនាញ: ${b.trackId}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // 5. Student Notes List
        if (uiState.notes.isNotEmpty()) {
            item {
                Text(
                    text = if (isKhmer) "កំណត់ត្រាសិក្សាទាំងអស់ (Study Notes)" else "Your Study Notes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(uiState.notes) { note ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ជំពូក #${note.chapterId} • មេរៀន ${note.lessonId}",
                                style = MaterialTheme.typography.labelSmall,
                                color = CyanPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = note.noteContent,
                                style = MaterialTheme.typography.bodySmall,
                                lineHeight = 18.sp
                            )
                        }

                        IconButton(onClick = { onDeleteNote(note.id) }, modifier = Modifier.size(28.dp)) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Chapter
import com.example.data.model.ComputerTrack
import com.example.data.model.DifficultyLevel
import com.example.ui.components.ChapterCard
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.EmeraldAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChaptersScreen(
    chapters: List<Chapter>,
    searchQuery: String,
    selectedTrack: ComputerTrack?,
    selectedDifficulty: DifficultyLevel?,
    onlyBookmarked: Boolean,
    onlyCompleted: Boolean,
    isKhmer: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onSelectTrack: (ComputerTrack?) -> Unit,
    onSelectDifficulty: (DifficultyLevel?) -> Unit,
    onToggleOnlyBookmarked: () -> Unit,
    onToggleOnlyCompleted: () -> Unit,
    onChapterClick: (Int) -> Unit,
    onBookmarkToggle: (Chapter) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("chapters_screen")
    ) {
        // Search & Filters Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_chapters_input"),
                placeholder = {
                    Text(
                        text = if (isKhmer) "ស្វែងរកជំពូក (ឧ. CPU, RAM, OS, IP, SQL, BSOD)..." else "Search 120 chapters (e.g. CPU, OS, Docker)...",
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = CyanPrimary
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = CyanPrimary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Track filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedTrack == null,
                    onClick = { onSelectTrack(null) },
                    label = { Text(if (isKhmer) "គ្រប់ជំនាញ (All 120)" else "All Tracks (120)", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CyanPrimary.copy(alpha = 0.2f),
                        selectedLabelColor = CyanPrimary
                    )
                )

                ComputerTrack.entries.forEach { track ->
                    FilterChip(
                        selected = selectedTrack == track,
                        onClick = { onSelectTrack(if (selectedTrack == track) null else track) },
                        label = {
                            Text(
                                text = if (isKhmer) track.titleKhmer.split(" ")[0] else track.titleEnglish.split(" ")[0],
                                fontSize = 11.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyanPrimary.copy(alpha = 0.2f),
                            selectedLabelColor = CyanPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Quick toggles: Bookmarked & Completed
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = onlyBookmarked,
                    onClick = onToggleOnlyBookmarked,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(14.dp))
                    },
                    label = { Text(if (isKhmer) "បានចំណាំ" else "Bookmarked", fontSize = 11.sp) }
                )

                FilterChip(
                    selected = onlyCompleted,
                    onClick = onToggleOnlyCompleted,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(14.dp), tint = EmeraldAccent)
                    },
                    label = { Text(if (isKhmer) "បានបញ្ចប់" else "Completed", fontSize = 11.sp) }
                )

                DifficultyLevel.entries.forEach { diff ->
                    FilterChip(
                        selected = selectedDifficulty == diff,
                        onClick = { onSelectDifficulty(if (selectedDifficulty == diff) null else diff) },
                        label = { Text(if (isKhmer) diff.titleKhmer else diff.titleEnglish, fontSize = 11.sp) }
                    )
                }
            }
        }

        // Chapters Count Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isKhmer) "បង្ហាញ ${chapters.size} ក្នុងចំណោម ១២០ ជំពូក" else "Showing ${chapters.size} of 120 Chapters",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (selectedTrack != null) {
                Text(
                    text = "ជំពូក ${selectedTrack.startChapter} - ${selectedTrack.endChapter}",
                    style = MaterialTheme.typography.labelSmall,
                    color = CyanPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Chapters List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (chapters.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = if (isKhmer) "រកមិនឃើញជំពូកដែលត្រូវនឹងការស្វែងរកទេ" else "No chapters match your criteria",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(chapters, key = { it.id }) { ch ->
                    ChapterCard(
                        chapter = ch,
                        isKhmer = isKhmer,
                        onChapterClick = { onChapterClick(ch.id) },
                        onBookmarkToggle = onBookmarkToggle
                    )
                }
            }
        }
    }
}

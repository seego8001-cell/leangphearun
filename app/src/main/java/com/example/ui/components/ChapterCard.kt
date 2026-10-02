package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Chapter
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.EmeraldAccent

@Composable
fun ChapterCard(
    chapter: Chapter,
    isKhmer: Boolean,
    onChapterClick: (Chapter) -> Unit,
    onBookmarkToggle: (Chapter) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onChapterClick(chapter) }
            .testTag("chapter_card_${chapter.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = if (chapter.isCompleted) {
            BorderStroke(1.5.dp, EmeraldAccent.copy(alpha = 0.5f))
        } else {
            BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Track + Chapter Number + Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = if (chapter.isCompleted) EmeraldAccent.copy(alpha = 0.2f) else CyanPrimary.copy(alpha = 0.15f),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "#${chapter.chapterNumber}",
                                color = if (chapter.isCompleted) EmeraldAccent else CyanPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TrackBadge(track = chapter.track, isKhmer = isKhmer)
                    Spacer(modifier = Modifier.width(6.dp))
                    DifficultyBadge(difficulty = chapter.difficulty, isKhmer = isKhmer)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (chapter.isCompleted) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = EmeraldAccent,
                            modifier = Modifier.size(20.dp).padding(end = 4.dp)
                        )
                    }

                    IconButton(
                        onClick = { onBookmarkToggle(chapter) },
                        modifier = Modifier.size(36.dp).testTag("bookmark_btn_${chapter.id}")
                    ) {
                        Icon(
                            imageVector = if (chapter.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (chapter.isBookmarked) CyanPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Primary Title
            Text(
                text = if (isKhmer) chapter.titleKhmer else chapter.titleEnglish,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Secondary Title (Subtext)
            Text(
                text = if (isKhmer) chapter.titleEnglish else chapter.titleKhmer,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Summary
            Text(
                text = if (isKhmer) chapter.summaryKhmer else chapter.summaryEnglish,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Footer info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${chapter.estimatedMinutes} នាទី",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "${chapter.lessonsCount} មេរៀនរង • ធ្វើតេស្ត Quiz",
                    style = MaterialTheme.typography.labelSmall,
                    color = CyanPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

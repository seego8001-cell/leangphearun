package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Chapter
import com.example.data.model.ComputerTrack
import com.example.ui.components.ChapterCard
import com.example.ui.components.StatCard
import com.example.ui.components.TrackBadge
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.CyanPrimary
import com.example.ui.theme.EmeraldAccent

@Composable
fun HomeScreen(
    isKhmer: Boolean,
    completedChaptersCount: Int,
    onNavigateToChapters: (ComputerTrack?) -> Unit,
    onNavigateToChapterDetail: (Int) -> Unit,
    onNavigateToAiTutor: () -> Unit,
    onNavigateToTroubleshooting: () -> Unit,
    featuredChapters: List<Chapter>,
    onToggleBookmark: (Chapter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 1. Hero Banner with Computer Academy Visual
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_banner_computer_1790383863307),
                        contentDescription = "Computer Learning Academy Banner",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color(0xFF090D16).copy(alpha = 0.95f)
                                    )
                                )
                            )
                    )

                    // Banner Text
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = CyanPrimary.copy(alpha = 0.25f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CyanPrimary)
                        ) {
                            Text(
                                text = "120 ជំពូក • 230+ មេរៀនកុំព្យូទ័រ",
                                color = CyanPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isKhmer) "បណ្ឌិត្យសភាវិទ្យាសាស្ត្រកុំព្យូទ័រ" else "Computer Science Academy",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = if (isKhmer) "រៀនពីមូលដ្ឋានគ្រឹះ Hardware រហូតដល់ AI & Security" else "Master hardware, OS, networking, security, code & AI",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1)
                        )
                    }
                }
            }
        }

        // 2. System ជួយបង្រៀន (AI Teaching Assistant Banner Card)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToAiTutor() }
                    .testTag("home_ai_tutor_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF132338)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyanPrimary.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = CyanPrimary.copy(alpha = 0.2f),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = CyanPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isKhmer) "ប្រព័ន្ធជួយបង្រៀន (AI Computer Tutor)" else "AI Teaching Assistant System",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CyanPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isKhmer) "ឆ្ងល់មេរៀនណា? លោកគ្រូឆ្លាតវៃជួយពន្យល់ ណែនាំកូដ និងដោះស្រាយបញ្ហាគ្រប់ពេល!" else "Ask questions, get step-by-step explanations, code reviews & diagnostics!",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFE2E8F0),
                            lineHeight = 18.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Chat",
                        tint = CyanPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // 3. Stats Overview
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = if (isKhmer) "ជំពូកសរុប" else "Total Chapters",
                    value = "120",
                    subtitle = if (isKhmer) "១០ ជំនាញស្នូល" else "10 Core Tracks",
                    iconColor = CyanPrimary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = if (isKhmer) "បានបញ្ចប់" else "Completed",
                    value = "$completedChaptersCount",
                    subtitle = "${(completedChaptersCount * 100) / 120}% នៃកម្រងសិក្សា",
                    iconColor = EmeraldAccent,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = if (isKhmer) "ជំនួយការ" else "AI Tutor",
                    value = "24/7",
                    subtitle = if (isKhmer) "ឆ្លើយភ្លាមៗ" else "Gemini Flash",
                    iconColor = AmberAccent,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 4. Ten Master Tracks
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isKhmer) "ជំនាញស្នូលទាំង ១០ (Tracks)" else "10 Core Computer Tracks",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (isKhmer) "ជ្រើសរើសជំនាញដើម្បីមើលមេរៀន" else "Select a track to view lessons",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = { onNavigateToChapters(null) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = CyanPrimary
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = if (isKhmer) "មើលទាំងអស់" else "View All", fontSize = 12.sp)
                }
            }
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ComputerTrack.entries) { track ->
                    Card(
                        modifier = Modifier
                            .width(160.dp)
                            .clickable { onNavigateToChapters(track) }
                            .testTag("track_card_${track.id}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            TrackBadge(track = track, isKhmer = isKhmer)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (isKhmer) track.titleKhmer else track.titleEnglish,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${track.totalChapters} ជំពូក (Ch ${track.startChapter}-${track.endChapter})",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // 5. Featured Lessons Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isKhmer) "ជំពូកសំខាន់ៗដែលគួរចាប់ផ្តើម" else "Featured Chapters to Start",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        items(featuredChapters) { ch ->
            ChapterCard(
                chapter = ch,
                isKhmer = isKhmer,
                onChapterClick = { onNavigateToChapterDetail(ch.id) },
                onBookmarkToggle = { onToggleBookmark(ch) }
            )
        }
    }
}

package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CalloutType
import com.example.data.model.ComputerTrack
import com.example.data.model.DifficultyLevel
import com.example.ui.theme.CodeBackgroundDark
import com.example.ui.theme.CodeTextCyan
import com.example.ui.theme.CodeTextGreen
import com.example.ui.theme.EmeraldAccent

@Composable
fun TrackBadge(
    track: ComputerTrack,
    isKhmer: Boolean = true,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (track) {
        ComputerTrack.HARDWARE -> Pair(Color(0xFF0284C7).copy(alpha = 0.15f), Color(0xFF38BDF8))
        ComputerTrack.OPERATING_SYSTEMS -> Pair(Color(0xFF8B5CF6).copy(alpha = 0.15f), Color(0xFFA78BFA))
        ComputerTrack.NETWORKING -> Pair(Color(0xFF059669).copy(alpha = 0.15f), Color(0xFF34D399))
        ComputerTrack.CYBERSECURITY -> Pair(Color(0xFFE11D48).copy(alpha = 0.15f), Color(0xFFFB7185))
        ComputerTrack.PROGRAMMING -> Pair(Color(0xFFD97706).copy(alpha = 0.15f), Color(0xFFFBBF24))
        ComputerTrack.WEB_APP_DEV -> Pair(Color(0xFF2563EB).copy(alpha = 0.15f), Color(0xFF60A5FA))
        ComputerTrack.DATABASES -> Pair(Color(0xFF7C3AED).copy(alpha = 0.15f), Color(0xFFC084FC))
        ComputerTrack.AI_DATA_SCIENCE -> Pair(Color(0xFF9333EA).copy(alpha = 0.15f), Color(0xFFE879F9))
        ComputerTrack.CLOUD_DEVOPS -> Pair(Color(0xFF0D9488).copy(alpha = 0.15f), Color(0xFF2DD4BF))
        ComputerTrack.IT_SUPPORT -> Pair(Color(0xFFEA580C).copy(alpha = 0.15f), Color(0xFFFB923C))
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = if (isKhmer) track.titleKhmer.split(" ")[0] else track.titleEnglish.split(" ")[0],
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DifficultyBadge(
    difficulty: DifficultyLevel,
    isKhmer: Boolean = true,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (difficulty) {
        DifficultyLevel.BEGINNER -> Pair(Color(0xFF10B981).copy(alpha = 0.15f), Color(0xFF34D399))
        DifficultyLevel.INTERMEDIATE -> Pair(Color(0xFFF59E0B).copy(alpha = 0.15f), Color(0xFFFBBF24))
        DifficultyLevel.ADVANCED -> Pair(Color(0xFFEF4444).copy(alpha = 0.15f), Color(0xFFF87171))
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = if (isKhmer) difficulty.titleKhmer else difficulty.titleEnglish,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = iconColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun InteractiveCodeBlock(
    code: String,
    language: String? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp)),
        color = CodeBackgroundDark
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFEF4444)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFF59E0B)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF10B981)))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = language ?: "CODE / TERMINAL",
                        color = Color(0xFF94A3B8),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                IconButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Computer Academy Code", code)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "ចម្លងកូដរួចរាល់! (Copied)", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(28.dp).testTag("copy_code_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy code",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = code,
                color = CodeTextGreen,
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun LessonCalloutBox(
    type: CalloutType,
    textKhmer: String,
    textEnglish: String,
    isKhmer: Boolean = true,
    modifier: Modifier = Modifier
) {
    val (icon, tint, bg) = when (type) {
        CalloutType.INFO -> Triple(Icons.Default.Info, Color(0xFF38BDF8), Color(0xFF0369A1).copy(alpha = 0.12f))
        CalloutType.WARNING -> Triple(Icons.Default.Warning, Color(0xFFFBBF24), Color(0xFFB45309).copy(alpha = 0.12f))
        CalloutType.PRO_TIP -> Triple(Icons.Default.Lightbulb, EmeraldAccent, Color(0xFF047857).copy(alpha = 0.12f))
        CalloutType.HARDWARE_SPEC -> Triple(Icons.Default.CheckCircle, Color(0xFFA78BFA), Color(0xFF6D28D9).copy(alpha = 0.12f))
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .border(1.dp, tint.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = if (isKhmer) textKhmer else textEnglish,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 20.sp
        )
    }
}

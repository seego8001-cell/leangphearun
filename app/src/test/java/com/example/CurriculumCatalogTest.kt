package com.example

import com.example.data.model.ComputerTrack
import com.example.data.repository.CurriculumCatalog
import com.example.data.repository.LessonContentProvider
import com.example.data.repository.QuizDataProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CurriculumCatalogTest {

    @Test
    fun verifyTotalChaptersCountIs120() {
        val chapters = CurriculumCatalog.allChapters
        assertEquals("Total chapters must be exactly 120", 120, chapters.size)
    }

    @Test
    fun verifyTenTracksDistribution() {
        assertEquals("There must be 10 core computer tracks", 10, ComputerTrack.entries.size)

        ComputerTrack.entries.forEach { track ->
            val chaptersInTrack = CurriculumCatalog.getChaptersForTrack(track)
            assertEquals("Each track must have 12 chapters", 12, chaptersInTrack.size)
        }
    }

    @Test
    fun verifyLessonContentProvider() {
        val ch1Lessons = LessonContentProvider.getLessonsForChapter(1)
        assertTrue("Chapter 1 must have detailed lessons", ch1Lessons.isNotEmpty())
        assertNotNull("First lesson must have title", ch1Lessons[0].titleKhmer)

        val ch120Lessons = LessonContentProvider.getLessonsForChapter(120)
        assertTrue("Chapter 120 must have lessons", ch120Lessons.isNotEmpty())
    }

    @Test
    fun verifyQuizDataProvider() {
        val quiz = QuizDataProvider.getQuizForChapter(1)
        assertTrue("Chapter 1 must have quiz questions", quiz.isNotEmpty())
        assertTrue("Quiz question has options", quiz[0].optionsKhmer.size >= 2)
    }
}

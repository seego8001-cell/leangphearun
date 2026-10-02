package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.local.AppDatabase
import com.example.data.repository.UserDataRepository
import com.example.ui.components.AppBottomNavigationBar
import com.example.ui.components.AppTopBar
import com.example.ui.navigation.Screen
import com.example.ui.screens.AiTutorScreen
import com.example.ui.screens.ChaptersScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LessonDetailScreen
import com.example.ui.screens.StudentProfileScreen
import com.example.ui.screens.TroubleshootingScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AiTutorViewModel
import com.example.ui.viewmodel.CurriculumViewModel
import com.example.ui.viewmodel.LessonViewModel
import com.example.ui.viewmodel.StudentProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val userDataRepository = UserDataRepository(database)

        setContent {
            MyApplicationTheme {
                MainApp(userDataRepository = userDataRepository)
            }
        }
    }
}

@Composable
fun MainApp(userDataRepository: UserDataRepository) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val curriculumViewModel: CurriculumViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return CurriculumViewModel(userDataRepository) as T
            }
        }
    )

    val lessonViewModel: LessonViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return LessonViewModel(userDataRepository) as T
            }
        }
    )

    val aiTutorViewModel: AiTutorViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return AiTutorViewModel(userDataRepository) as T
            }
        }
    )

    val profileViewModel: StudentProfileViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return StudentProfileViewModel(userDataRepository) as T
            }
        }
    )

    val curriculumUiState by curriculumViewModel.uiState.collectAsState()
    val isKhmer = curriculumUiState.isKhmerLanguage

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Chapters.route,
        Screen.AiTutor.route,
        Screen.Troubleshooting.route,
        Screen.Profile.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showBottomBar) {
                AppTopBar(
                    title = if (isKhmer) "រៀនកុំព្យូទ័រ" else "Computer Academy",
                    subtitle = if (isKhmer) "១២០ ជំពូក • ប្រព័ន្ធជួយបង្រៀន" else "120 Chapters • AI Tutor System",
                    isKhmer = isKhmer,
                    onToggleLanguage = { curriculumViewModel.toggleLanguage() },
                    onNavigateToAiTutor = { navController.navigate(Screen.AiTutor.route) }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        if (currentRoute != route) {
                            navController.navigate(route) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                val profileState by profileViewModel.uiState.collectAsState()
                val featured = remember(curriculumUiState.allChapters) {
                    val keyIds = listOf(1, 13, 25, 37, 49, 116)
                    curriculumUiState.allChapters.filter { it.id in keyIds }
                }

                HomeScreen(
                    isKhmer = isKhmer,
                    completedChaptersCount = profileState.completedChaptersCount,
                    onNavigateToChapters = { track ->
                        curriculumViewModel.selectTrack(track)
                        navController.navigate(Screen.Chapters.route)
                    },
                    onNavigateToChapterDetail = { chId ->
                        navController.navigate(Screen.LessonDetail.createRoute(chId))
                    },
                    onNavigateToAiTutor = {
                        navController.navigate(Screen.AiTutor.route)
                    },
                    onNavigateToTroubleshooting = {
                        navController.navigate(Screen.Troubleshooting.route)
                    },
                    featuredChapters = featured,
                    onToggleBookmark = { ch -> curriculumViewModel.toggleBookmark(ch) }
                )
            }

            composable(Screen.Chapters.route) {
                ChaptersScreen(
                    chapters = curriculumUiState.filteredChapters,
                    searchQuery = curriculumUiState.searchQuery,
                    selectedTrack = curriculumUiState.selectedTrack,
                    selectedDifficulty = curriculumUiState.selectedDifficulty,
                    onlyBookmarked = curriculumUiState.onlyBookmarked,
                    onlyCompleted = curriculumUiState.onlyCompleted,
                    isKhmer = isKhmer,
                    onSearchQueryChange = { q -> curriculumViewModel.setSearchQuery(q) },
                    onSelectTrack = { t -> curriculumViewModel.selectTrack(t) },
                    onSelectDifficulty = { d -> curriculumViewModel.setDifficulty(d) },
                    onToggleOnlyBookmarked = { curriculumViewModel.toggleOnlyBookmarked() },
                    onToggleOnlyCompleted = { curriculumViewModel.toggleOnlyCompleted() },
                    onChapterClick = { chId ->
                        navController.navigate(Screen.LessonDetail.createRoute(chId))
                    },
                    onBookmarkToggle = { ch -> curriculumViewModel.toggleBookmark(ch) }
                )
            }

            composable(
                route = Screen.LessonDetail.route,
                arguments = listOf(navArgument("chapterId") { type = NavType.IntType })
            ) { backStackEntry ->
                val chapterId = backStackEntry.arguments?.getInt("chapterId") ?: 1

                LaunchedEffect(chapterId) {
                    lessonViewModel.loadChapter(chapterId)
                }

                val lessonUiState by lessonViewModel.uiState.collectAsState()

                LessonDetailScreen(
                    uiState = lessonUiState,
                    isKhmer = isKhmer,
                    onBackClick = { navController.popBackStack() },
                    onSelectLesson = { idx -> lessonViewModel.selectLesson(idx) },
                    onNewNoteChange = { txt -> lessonViewModel.updateNewNoteText(txt) },
                    onSaveNote = { lessonViewModel.saveCurrentNote() },
                    onDeleteNote = { id -> lessonViewModel.deleteNote(id) },
                    onSelectQuizAnswer = { qIdx, optIdx -> lessonViewModel.selectQuizAnswer(qIdx, optIdx) },
                    onSubmitQuiz = { lessonViewModel.submitQuiz() },
                    onMarkCompleted = { lessonViewModel.markCompleted() },
                    onToggleBookmark = { lessonViewModel.toggleBookmark() },
                    onAskAiAboutLesson = { ctxName ->
                        aiTutorViewModel.setChapterContext(ctxName)
                        navController.navigate(Screen.AiTutor.route)
                    }
                )
            }

            composable(Screen.AiTutor.route) {
                val aiTutorUiState by aiTutorViewModel.uiState.collectAsState()

                AiTutorScreen(
                    uiState = aiTutorUiState,
                    isKhmer = isKhmer,
                    onInputChange = { txt -> aiTutorViewModel.updateInput(txt) },
                    onSendMessage = { prompt -> aiTutorViewModel.sendMessage(prompt) },
                    onClearChat = { aiTutorViewModel.clearChatHistory() },
                    onRemoveContext = { aiTutorViewModel.setChapterContext("") }
                )
            }

            composable(Screen.Troubleshooting.route) {
                TroubleshootingScreen(
                    isKhmer = isKhmer,
                    onAskAiTutor = { prompt ->
                        aiTutorViewModel.sendMessage(prompt)
                        navController.navigate(Screen.AiTutor.route)
                    }
                )
            }

            composable(Screen.Profile.route) {
                val profileUiState by profileViewModel.uiState.collectAsState()

                StudentProfileScreen(
                    uiState = profileUiState,
                    isKhmer = isKhmer,
                    onNavigateToChapter = { chId ->
                        navController.navigate(Screen.LessonDetail.createRoute(chId))
                    },
                    onDeleteNote = { id -> profileViewModel.deleteNote(id) }
                )
            }
        }
    }
}

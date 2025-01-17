package dev.helena.gardenofeden_ccl3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.screens.HomeScreen
import dev.helena.gardenofeden_ccl3.ui.screens.JournalEntryDetailScreen
import dev.helena.gardenofeden_ccl3.ui.screens.LandingScreen
import dev.helena.gardenofeden_ccl3.ui.screens.WelcomeScreen

@Composable
fun AppNavigation(entryViewModel: EntryViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("landing") {
            LandingScreen(navController, entryViewModel)
        }
        composable("home") {
            HomeScreen(navController, entryViewModel)
        }
        composable(
            route = "journal_detail_screen/{entryId}",
            arguments = listOf(navArgument("entryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getInt("entryId")?.toLong()
            if (entryId != null) {
                // Pass the entryId and entryViewModel to the detail screen
                JournalEntryDetailScreen(navController = navController, entryId = entryId, entryViewModel = entryViewModel)
            }
        }
    }
}


package dev.helena.gardenofeden_ccl3.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.screens.HomeScreen
import dev.helena.gardenofeden_ccl3.ui.screens.JournalEntryDetailScreen
import dev.helena.gardenofeden_ccl3.ui.screens.LandingScreen
import dev.helena.gardenofeden_ccl3.ui.screens.WelcomeScreen

@Composable
fun AppNavigation(entryViewModel: EntryViewModel) {
    val navController = rememberNavController()

    // Always start with the "welcome" screen
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
        composable("journal_detail_screen/{entryId}") { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId")?.toLongOrNull()
            if (entryId != null) {
                JournalEntryDetailScreen(entryId, entryViewModel)
            } else {
                Text("Invalid entry ID.")
            }
        }
    }
}


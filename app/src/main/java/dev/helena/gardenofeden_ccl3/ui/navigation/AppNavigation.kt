package dev.helena.gardenofeden_ccl3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.screens.HomeScreen
import dev.helena.gardenofeden_ccl3.ui.screens.LandingScreen
import dev.helena.gardenofeden_ccl3.ui.screens.WelcomeScreen

@Composable
fun AppNavigation(entryViewModel: EntryViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController, entryViewModel)
        }
        composable("landing") {
            LandingScreen(navController, entryViewModel)
        }
        composable("home") {
            HomeScreen(navController, entryViewModel)
        }
    }
}

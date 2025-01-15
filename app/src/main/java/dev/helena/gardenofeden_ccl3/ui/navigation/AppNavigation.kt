package dev.helena.gardenofeden_ccl3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.helena.gardenofeden_ccl3.ui.screens.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController)
        }

        composable("landingscreen") {
            WelcomeScreen(navController)
        }

        composable("home") {  }

    }
}
package dev.helena.gardenofeden_ccl3.ui.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar {
        val items = listOf("home_tab" to "Home", "journal_tab" to "Journal")
        items.forEach { (route, label) ->
            val isSelected = currentRoute == route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    // Navigate only if not already selected
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            // Prevent stacking the same route multiple times
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { /* Optional: Add an icon */ },
                label = { Text(label) }
            )
        }
    }
}

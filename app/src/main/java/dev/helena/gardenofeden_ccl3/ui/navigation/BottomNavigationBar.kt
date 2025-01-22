package dev.helena.gardenofeden_ccl3.ui.navigation

import android.content.res.Resources.Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.filled.FilterVintage
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import dev.helena.gardenofeden_ccl3.ui.theme.Green
import dev.helena.gardenofeden_ccl3.ui.theme.LemonDark
import dev.helena.gardenofeden_ccl3.ui.theme.LemonLight

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White
    ) {
        val items = listOf(
            "home_tab" to Pair ("My Garden", Icons.Filled.FilterVintage),
            "journal_tab" to Pair ("Journals", Icons.Filled.Book)
        )
        items.forEach { (route, pair) ->
            val (label, icon) = pair
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
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Green,
                    selectedTextColor = Green,
                    unselectedIconColor = Green,
                    unselectedTextColor = Green,
                    indicatorColor = LemonLight,
                )
            )
        }
    }
}

package dev.helena.gardenofeden_ccl3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.components.GardenView
import dev.helena.gardenofeden_ccl3.ui.navigation.BottomNavigationBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign


@Composable
fun HomeScreen(navController: NavController, entryViewModel: EntryViewModel) {

    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "home_tab",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home_tab") {
                VirtualGardenScreen(entryViewModel)
            }
            composable("journal_tab") {
                JournalScreen(navController, entryViewModel)
            }
        }
    }
}


@Composable
fun VirtualGardenScreen(entryViewModel: EntryViewModel) {
    val entries by entryViewModel.entries.observeAsState(initial = emptyList()) // Observe entries

    var growthTriggered by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB2DFDB))
            .clickable { growthTriggered = true } // Trigger plant growth on click
            .padding(16.dp)
    ) {
        if (entries.isEmpty()) {
            Text(
                text = "No plants yet. Log your mood to grow your garden!",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // Ensure the growthTriggered is passed to GardenView
            GardenView(entries = entries, growthTriggered = growthTriggered)
        }
    }
}

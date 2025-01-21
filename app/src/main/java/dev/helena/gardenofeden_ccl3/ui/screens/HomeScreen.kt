package dev.helena.gardenofeden_ccl3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.helena.gardenofeden_ccl3.ui.viewmodel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.components.GardenView
import dev.helena.gardenofeden_ccl3.ui.navigation.BottomNavigationBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import dev.helena.gardenofeden_ccl3.ui.theme.MintLeaf
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info


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
    val growthTriggered = remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) } // State for info dialog visibility

    // Trigger automatic growth when entering the screen
    LaunchedEffect(Unit) {
        growthTriggered.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White, // Start color
                        MintLeaf // End color
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {// Title at the center
            Text(
                text = "Garden of Eden",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MintLeaf,
                modifier = Modifier.align(Alignment.Center)
            )
            // Information Icon
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { showInfoDialog = true }
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Information Icon",
                    tint = MintLeaf,
                    modifier = Modifier.size(32.dp) // Adjust icon size for better visuals
                )
            }
        }


        // Garden Section
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (entries.isEmpty()) {
                Text(
                    text = "No plants yet. Log your mood to grow your garden!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // Pass the plantMessage callback to GardenView
                GardenView(entries = entries, growthTriggered = growthTriggered.value)
            }
        }
    }

    // Info Dialog
    if (showInfoDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { showInfoDialog = false } // Close dialog when background is clicked
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "For what are the flowers?",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MintLeaf,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "The Garden of Eden is your personal garden where each flower represents an emotion you have logged. Watch as your emotions grow and bloom into a beautiful garden!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
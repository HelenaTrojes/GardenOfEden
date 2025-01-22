package dev.helena.gardenofeden_ccl3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.sp
import dev.helena.gardenofeden_ccl3.ui.theme.DarkGreen
import dev.helena.gardenofeden_ccl3.ui.theme.Green
import dev.helena.gardenofeden_ccl3.ui.theme.Rose


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
    val entries by entryViewModel.entries.observeAsState(initial = emptyList()) // observe entries
    val growthTriggered = remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) } // State for info dialog visibility

    // automatic growth when entering the screen
    LaunchedEffect(Unit) {
        growthTriggered.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
//        .systemBarsPadding(),
//            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 8.dp)
        ) {
            Text(
                text = "Your Garden of Eden",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 30.sp
            )
            //Underline
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 50.dp),
                thickness = 3.dp,
                color = Rose
            )
            // Information Icon
            Box(
                modifier = Modifier
                    .clickable { showInfoDialog = true }
                    .padding(top = 10.dp)
            ) {
                Icon(

                    imageVector = Icons.Filled.Info,
                    contentDescription = "Information Icon",
                    tint = Green,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Garden Section
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGreen)
        ) {
            if (entries.isEmpty()) {
                Text(
                    text = "No plants yet. Log your mood tomorrow to grow your garden!",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 10.dp, end = 10.dp)
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
                .clickable { showInfoDialog = false }
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
                        color = Green,
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

package dev.helena.gardenofeden_ccl3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.helena.gardenofeden_ccl3.ui.viewmodel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.components.JournalEntryCard
import dev.helena.gardenofeden_ccl3.ui.theme.Green
import dev.helena.gardenofeden_ccl3.ui.theme.Rose


@Composable
fun JournalScreen(
    navController: NavController,
    entryViewModel: EntryViewModel
) {
    val entries by entryViewModel.entries.observeAsState(emptyList())
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val paddingHorizontal = if (screenWidth > 600) 40.dp else 20.dp // Padding for different screen sizes
    val verticalSpacing = if (screenWidth > 600) 30.dp else 17.dp // Spacing for different screen sizes

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("landing")
                },
                containerColor = Green,
                contentColor = Color.Black
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Mood and a Journal")
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            val titleFontSize = if (screenWidth > 600) 32.sp else 30.sp
            Text(
                text = "Your Journals",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                fontSize = titleFontSize,
                textAlign = TextAlign.Center,
                modifier = Modifier
//                    .padding(top = 10.dp)
                    .fillMaxWidth()
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 50.dp),
                thickness = 3.dp,
                color = Rose
            )
            if (entries.isEmpty()) {
                // Display "No Entries" in the middle of the screen
                Text(
                    text = "No Entries",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Green
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = paddingHorizontal),
                    fontSize = 20.sp
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = paddingHorizontal)
                ) {
                    items(entries) { entry ->
                        JournalEntryCard(
                            entry = entry,
                            onClick = {
                                navController.navigate("journal_detail_screen/${entry.id}")
                            }
                        )
                        Spacer(modifier = Modifier.height(verticalSpacing))
                    }
                }
            }
        }
    }
}
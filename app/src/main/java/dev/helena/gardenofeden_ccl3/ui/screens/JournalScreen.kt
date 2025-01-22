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
import dev.helena.gardenofeden_ccl3.ui.theme.Rose

@Composable
fun JournalScreen(
    navController: NavController,
    entryViewModel: EntryViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val paddingHorizontal = when {
        screenWidth > 600 -> 40.dp // larger padding for tablets
        else -> 20.dp // default padding for phones
    }

    val verticalSpacing = when {
        screenWidth > 600 -> 30.dp // larger spacing for tablets
        else -> 17.dp // default spacing for phones
    }

    // Observe the list of entries
    val entries by entryViewModel.entries.observeAsState(emptyList())


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val titleFontSize = when {
            screenWidth > 600 -> 32.sp // larger font for tablets and larger screens
            else -> 30.sp // default size for phones
        }
        Text(
            text = "Your Journals",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            fontSize = titleFontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 50.dp),
            thickness = 3.dp,
            color = Rose
        )

        Spacer(modifier = Modifier.height(20.dp))

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = paddingHorizontal)
    ) {
        println("Entries size: ${entries.size}")
        items(entries) { entry ->

            JournalEntryCard(
                entry = entry,
                onClick =  {
                    println("Navigating to entry ID: ${entry.id}")
                    navController.navigate("journal_detail_screen/${entry.id}")
                    }
                )
            Spacer(modifier = Modifier.height(verticalSpacing))
        }
    }
    }
}
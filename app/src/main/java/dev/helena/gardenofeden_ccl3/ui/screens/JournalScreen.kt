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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.components.JournalEntryCard
import dev.helena.gardenofeden_ccl3.ui.theme.LemonLight

@Composable
fun JournalScreen(
    navController: NavController,
    entryViewModel: EntryViewModel
) {

    // Observe the list of entries
    val entries by entryViewModel.entries.observeAsState(emptyList())

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(LemonLight)
    ) {
        Text(
            text = "Your journal",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 30.dp)
                .padding(top = 25.dp)
                .fillMaxWidth()
        )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        println("Entries size: ${entries.size}")
        items(entries) { entry ->

            JournalEntryCard(
                entry = entry,
                onClick =  {
                    println("Navigating to entry ID: ${entry.id}")
                    navController.navigate("journal_detail_screen/${entry.id}")
                        println("Navigated to entry bitch")

                    }
                )
            Spacer(modifier = Modifier.height(17.dp))
        }
    }
    }
}

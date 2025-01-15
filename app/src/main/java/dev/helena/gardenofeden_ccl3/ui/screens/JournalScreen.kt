package dev.helena.gardenofeden_ccl3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel
import java.time.LocalDate

@Composable
fun JournalScreen(entryViewModel: EntryViewModel) {
    // Observe the list of entries
    val entries by entryViewModel.entries.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(entries) { entry ->
            JournalEntryCard(entry)
        }
    }
}

@Composable
fun JournalEntryCard(entry: EntryEntity) {
    val date = LocalDate.ofEpochDay(entry.date / 86400000L) // Convert timestamp to LocalDate
    val formattedDate = date.toString() // Format the date as needed

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = "Question: ${entry.question}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Date: $formattedDate",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

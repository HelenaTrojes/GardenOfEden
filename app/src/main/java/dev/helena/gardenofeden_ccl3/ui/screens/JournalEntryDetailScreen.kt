package dev.helena.gardenofeden_ccl3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel

@Composable
fun JournalEntryDetailScreen(
    navController: NavController,
    entryId: Long?,
    entryViewModel: EntryViewModel
) {
    // Observing the entry details based on entryId
    val entry by entryViewModel.entryDetails.observeAsState()
    val showDialog = remember { mutableStateOf(false) } //state for confirmation dialog

    // LaunchedEffect to fetch the entry details when entryId changes
    LaunchedEffect(entryId) {
        println("JournalDetailScreen: LaunchedEffect with entryID = $entryId")
        if (entryId != null) {
            entryViewModel.getEntryById(entryId)
        }
    }

    // Show loading or error message based on entryId
    if (entryId == null || entryId == -1L) {
        println("JournalDetailScreen: Invalid entryId received ($entryId)")
        Text("Loading...")
    } else {
        println("JournalDetailScreen: Valid entryId received ($entryId)")
        Text("Entry ID: $entryId")
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6))
            .padding(20.dp)
    ) {
        //Back Button
        IconButton(
            onClick = { navController.popBackStack()  },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 30.dp, start = 7.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        //Delete Button
        IconButton(
            onClick = {showDialog.value = true},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp, end = 7.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                tint = Color.Black
            )
        }
// Confirmation dialog
        if (showDialog.value) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = "Delete Entry") },
                text = { Text(text = "Are you sure you want to delete this entry? You won't get another entry for today") },
                confirmButton = {
                    androidx.compose.material3.TextButton(
                        onClick = {
                            showDialog.value = false
                            entry?.let {
                                entryViewModel.deleteEntry(it)
                                navController.popBackStack() // Navigate back
                            }
                        }
                    ) {
                        Text("Yes", color = Color.Red)
                    }
                },
                dismissButton = {
                    androidx.compose.material3.TextButton(
                        onClick = { showDialog.value = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Displaying details of the specific entry
        entry?.let {
            // Mood of entry
            Text(
                text = "Mood: ${it.mood}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Question of entry
            Text(
                text = "Question of the day",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                    .padding(16.dp)
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    text = it.question,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(35.dp))

            // Answer of entry
            Text(
                text = "Your answer",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                    .padding(16.dp)
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    text = it.answer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }

        } ?:

        Text(
            text = "No entry found",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

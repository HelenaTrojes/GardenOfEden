package dev.helena.gardenofeden_ccl3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun JournalEntryDetailScreen(
    navController: NavController,
    entryId: Long?,
    entryViewModel: EntryViewModel
) {
    // Observing the entry details based on entryId
    val entry by entryViewModel.entryDetails.observeAsState()
    var isEditing by remember { mutableStateOf(false) }
    var editedAnswer by remember { mutableStateOf("") }


    val date = Instant.ofEpochMilli(entry?.date ?: 0L).atZone(ZoneId.systemDefault()).toLocalDate()
    val formattedDate = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

    val moodToEmojiMap = mapOf(
        "Happy" to "😊",
        "Sad" to "😢",
        "Surprised" to "😲",
        "Silly" to "😜",
        "Angry" to "😡"
    )

    // LaunchedEffect to fetch the entry details when entryId changes
    LaunchedEffect(entryId) {
        if (entryId != null) {
            entryViewModel.getEntryById(entryId)
        }
    }

    LaunchedEffect(entry) {
        entry?.let {
            editedAnswer = it.answer
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6))
            .padding(20.dp)
    ) {
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
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Displaying details of the specific entry
        entry?.let {
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // get the emoj for the selected mood
                val moodEmoji = moodToEmojiMap[it.mood] ?: it.mood

                // Mood of entry
                Text(
                    text = "Mood: $moodEmoji",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Text(
                text = "You felt ${it.mood} on $formattedDate ",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .padding(16.dp)
                .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
            // Question of entry
            Text(
                text = "Question of the day",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(30.dp))
                    .padding()
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = it.question,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                    textAlign = TextAlign.Center
                )
            }
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = if (isEditing) editedAnswer else it.answer,
                    onValueChange = { if (isEditing) editedAnswer = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .border(2.dp, Color.DarkGray, RoundedCornerShape(10.dp)),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                    enabled = isEditing,
                    readOnly = !isEditing
                )

                IconButton(
                    onClick = {
                        if (isEditing) {
                            val updatedEntry = it.copy(answer = editedAnswer)
                            entryViewModel.updateEntry(updatedEntry)

                            if (entryId != null) {
                                entryViewModel.getEntryById(entryId)
                            }
                        } else {
                            editedAnswer = it.answer
                        }
                        isEditing = !isEditing
                    }
                ) {
                    Icon(
                        imageVector = if (isEditing) Icons.Filled.Check else Icons.Filled.Edit,
                        contentDescription = if (isEditing) "Save" else "Edit"
                    )
                }

            }

        } ?:

        Text(
            text = "No entry found",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
package dev.helena.gardenofeden_ccl3.ui.screens

import android.util.Log
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.helena.gardenofeden_ccl3.ui.theme.Green
import dev.helena.gardenofeden_ccl3.ui.viewmodel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.theme.Rose
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEntryDetailScreen(
    navController: NavController,
    entryId: Long?,
    entryViewModel: EntryViewModel
) {

    // Observing the entry details based on entryId
    val entry by entryViewModel.entryDetails.observeAsState()
    Log.i("test", "from beginning ${entry}")
    val showDialog = remember { mutableStateOf(false) } //state for confirmation dialog
    var isEditing by remember { mutableStateOf(false) }
    var editedAnswer by remember { mutableStateOf(TextFieldValue("")) }

    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()

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
            Log.i("test", "from launch ${entry}")
        }
    }

    LaunchedEffect(entry) {
        entry?.let {
            editedAnswer = TextFieldValue(it.answer)
        }
    }

    LaunchedEffect(isEditing) {
        if (isEditing) {
            editedAnswer = TextFieldValue(
                text = entry?.answer ?: "",
                selection = TextRange(entry?.answer?.length ?: 0)
            )
            focusRequester.requestFocus()
        } else {
            scrollState.scrollTo(0)
        }
    }

    LaunchedEffect(editedAnswer) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 20.dp, start = 5.dp)
            .imePadding()
    ) {
        //Back Button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 20.dp)
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
            .fillMaxSize()
            .padding(16.dp)
            // .padding(start = 10.dp, top = 100.dp)
            .imePadding(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Your Journals",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 36.dp)
                .fillMaxWidth()
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 50.dp),
            thickness = 3.dp,
            color = Rose
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Displaying details of the specific entry
        entry?.let {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .background(Rose, RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .imePadding(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {

                Row(
                    modifier = Modifier
                ) {
                    // get the emoji for the selected mood
                    val moodEmoji = moodToEmojiMap[it.mood] ?: it.mood

                    // Mood of entry
                    Text(
                        text = "Mood: $moodEmoji",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = "You felt ${it.mood} on $formattedDate ",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    color = Color.Black,

                    )
                Spacer(modifier = Modifier.height(40.dp))

                // Question of the day
                Text(
                    text = "Question of the day",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(7.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Green, RoundedCornerShape(30.dp))
                        .padding()
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 10.dp),
                    Alignment.Center
                ) {
                    Text(
                        text = it.question,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))


                // Answer of entry
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Your answer",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .weight(1f),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    IconButton(
                        onClick = {
                            if (isEditing) {
                                val updatedEntry = it.copy(answer = editedAnswer.text)
                                entryViewModel.updateEntry(updatedEntry)

                                if (entryId != null) {
                                    entryViewModel.getEntryById(entryId)
                                    Log.i("test", "from idk where ${entry}")

                                }
                            } else {
                                editedAnswer = TextFieldValue(it.answer)
                            }
                            isEditing = !isEditing
                        }
                    ) {
                        Icon(
                            imageVector = if (isEditing) Icons.Filled.Check else Icons.Filled.Edit,
                            contentDescription = if (isEditing) "Save" else "Edit"
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    //Delete Button
                    IconButton(
                        onClick = { showDialog.value = true },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = Color.Black
                        )
                    }

                }

                // Confirmation dialog
                if (showDialog.value) {
                    androidx.compose.material3.AlertDialog(
                        onDismissRequest = { showDialog.value = false },
                        title = { Text(text = "Delete Journal") },
                        text = { Text(text = "Are you sure you want to delete this entry? Please note, you won’t be able to create a new entry for today.") },
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


                Spacer(modifier = Modifier.height(5.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                        .padding(5.dp)
                        .border(2.dp, Green, RoundedCornerShape(10.dp))
                        .background(Color.White, RoundedCornerShape(10.dp))
                ) {
                    TextField(
                        value = editedAnswer,
                        onValueChange = { if (isEditing) editedAnswer = it },
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .focusRequester(focusRequester),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                        ),
                        enabled = isEditing,
                        readOnly = !isEditing,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        maxLines = Int.MAX_VALUE,
                        singleLine = false,
                    )
                }
            }
        }
    }
}
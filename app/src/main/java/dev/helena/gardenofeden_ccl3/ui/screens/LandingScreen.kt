package dev.helena.gardenofeden_ccl3.ui.screens

import android.content.Context
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.theme.LemonLight
import dev.helena.gardenofeden_ccl3.ui.theme.MintLeaf
import dev.helena.gardenofeden_ccl3.ui.theme.RoseLight
import dev.helena.gardenofeden_ccl3.util.JsonUtils
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.min

val START_DATE: LocalDate = LocalDate.of(2025, 1, 1)

@Composable
fun LandingScreen(navController: NavController, entryViewModel: EntryViewModel) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val questionOfTheDay = remember { getQuestionOfTheDay(context) }
    var selectedMood by remember { mutableStateOf("") }
    var answerText by remember { mutableStateOf("") }

    //for the input field content
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(LemonLight)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // No ripple effect
            ) {
                // Clear focus and hide the keyboard when tapping outside
                focusManager.clearFocus()
                keyboardController?.hide()
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
               // .imePadding()
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // Mood Of The Day Section
            Text(
                text = "Mood of the Day",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 20.dp, end = 20.dp)
            ) {
                val moods = listOf(
                    "😊" to "Happy",
                    "😢" to "Sad",
                    "😲" to "Surprised",
                    "😜" to "Silly",
                    "😡" to "Angry"
                )
                moods.forEach { (emoji, mood) ->
                    val isSelected = selectedMood == mood // check if the emoji is selected

                    Text(
                        text = emoji,
                        fontSize = 28.sp,
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { selectedMood = mood }
                            .padding(horizontal = 4.dp)
                            .background(
                                color = if (isSelected) MintLeaf else Color.Transparent,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (selectedMood.isNotEmpty()) {
                Text(
                    text = "You feel $selectedMood today!",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 17.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 26.dp)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Question Of The Day Section
            Column(
                modifier = Modifier
                   // .imePadding()
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Question of the Day",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    color = Color.Black
                )
                Text(
                    text = questionOfTheDay,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = answerText,
                    onValueChange = { answerText = it },
                    label = { Text("Your answer") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 30.dp, max = 70.dp)
                        .padding(bottom = 7.dp),
                       // .verticalScroll(scrollState) // Make the text area scrollable
                       // .imePadding(), // Adjusts padding when the keyboard is visible

                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide() // Hide keyboard when done
                        }
                    ),
                    maxLines = Int.MAX_VALUE,
                    singleLine = false
                )

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        if (selectedMood.isNotEmpty() && answerText.isNotEmpty()) {
                            // creates entry and saves it
                            val entry = EntryEntity(
                                mood = selectedMood,
                                question = questionOfTheDay,
                                answer = answerText,
                                date = System.currentTimeMillis() // Current timestamp
                            )
                            entryViewModel.insertEntry(entry)  // Save to the database
                            entryViewModel.saveLastEntryDate(System.currentTimeMillis()) // save last entry date
                            navController.navigate("home") {
                                popUpTo("landing") { inclusive = true } // removes the landing page from back stack
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please select a mood and provide an answer.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MintLeaf
                    )
                ) {
                    Text(
                        text = "Save answer",
                        color = Color.Black
                    )
                }
            }
        }
    }
}


fun getQuestionOfTheDay(context: Context): String {
    val questions = JsonUtils.readQuestionsFromJson(context)

    // in case that question list is empty
    if (questions.isEmpty()) {
        return "No questions available for today"
    }

    // calculating here how many days have passed since the start date
    val today = LocalDate.now()
    val daysPassed = ChronoUnit.DAYS.between(START_DATE, today).toInt()

    // this will give a sequential index for each day
    val index = daysPassed % questions.size // cycles back after the questions

    // we go through the questions again
    return questions[index].question
}

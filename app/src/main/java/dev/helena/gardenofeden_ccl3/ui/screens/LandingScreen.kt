package dev.helena.gardenofeden_ccl3.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity
import dev.helena.gardenofeden_ccl3.ui.theme.DarkGreen
import dev.helena.gardenofeden_ccl3.ui.theme.Green
import dev.helena.gardenofeden_ccl3.ui.viewmodel.EntryViewModel
import dev.helena.gardenofeden_ccl3.util.JsonUtils
import java.time.LocalDate
import java.time.temporal.ChronoUnit

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

    LaunchedEffect(answerText) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    BoxWithConstraints (
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(Color.White)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // No ripple effect
            ) {
                // Clear focus and hide the keyboard when tapping outside
                focusManager.clearFocus()
                keyboardController?.hide()
            }

    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
               // .verticalScroll(scrollState)
               // .imePadding()
                .padding(top = screenHeight * 0.1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // Mood Of The Day Section
            Text(
                text = "Mood of the Day",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                fontSize = (screenWidth * 0.07f).value.sp,
                modifier = Modifier.padding(bottom = screenWidth * 0.02f),
                color = Color.Black
            )
            //Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = screenWidth * 0.05f)
                   // .padding(bottom = 24.dp, start = 20.dp, end = 20.dp)
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
                        fontSize = (screenWidth * 0.08f).value.sp,
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { selectedMood = mood }
                            .padding(horizontal = screenWidth * 0.02f)
                            .background(
                                color = if (isSelected) DarkGreen else Color.Transparent,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(screenWidth * 0.02f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(screenHeight * 0.05f))
                }
            }

            if (selectedMood.isNotEmpty()) {
                Text(
                    text = "You feel $selectedMood today!",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = (screenWidth * 0.04f).value.sp,
                    color = Color.DarkGray,
                )
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.02f))

            // Question Of The Day Section
            Column(
                modifier = Modifier
                   //.imePadding()
                    .padding(horizontal = screenWidth * 0.05f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Write in your journal or answer today's question",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = (screenWidth * 0.07f).value.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = screenHeight * 0.01f),
                    thickness = 3.dp,
                    color = DarkGreen
                )
                Text(
                    text = questionOfTheDay,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = (screenWidth * 0.045f).value.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(vertical = screenHeight * 0.02f)
                )

                OutlinedTextField(
                    value = answerText,
                    onValueChange = { newText ->
                        answerText = newText },
                    label = { Text("Your answer") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp, max = screenHeight * 0.5f)
                        //.height(350.dp)
                        .padding(5.dp)
                        .verticalScroll(scrollState),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide() // Hide keyboard when done
                            focusManager.clearFocus()
                        }
                    ),
                    maxLines = Int.MAX_VALUE,
                    singleLine = false
                )

                Spacer(modifier = Modifier.height(screenHeight * 0.03f))


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
                         .padding(vertical = screenHeight * 0.02f)
                        .height(50.dp)
                      //  .padding(horizontal = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = androidx.compose.foundation.shape.CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor  = Green,
                    )
                ) {
                    Text(
                        text = "Save answer",
                        color = Color.Black,
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

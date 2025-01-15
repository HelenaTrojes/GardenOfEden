package dev.helena.gardenofeden_ccl3.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel
import dev.helena.gardenofeden_ccl3.util.JsonUtils
import java.time.LocalDate
import java.time.temporal.ChronoUnit

val START_DATE: LocalDate = LocalDate.of(2025, 1, 1)
@Composable
fun LandingScreen(navController: NavController, entryViewModel: EntryViewModel) {
    val context = LocalContext.current

    val questionOfTheDay = remember { getQuestionOfTheDay(context) }
    var selectedMood by remember { mutableStateOf("") }
    var answerText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Mood Of The Day Section
        Text(
            text = "Mood of the Day",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .padding(horizontal = 25.dp)
        ) {
            val moods = listOf(
                "😊" to "happy",
                "😢" to "sad",
                "😲" to "surprised",
                "😜" to "silly",
                "😡" to "angry"
            )
            moods.forEach { (emoji, mood) ->
                val isSelected = selectedMood == mood // check if the emoji is selected

                Text(
                    text = emoji,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { selectedMood = mood }
                        .padding(horizontal = 8.dp)
                        .background(
                            color = if (isSelected) Color(0xFF0096C7).copy(alpha = 0.3f) else Color.Transparent,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        if (selectedMood.isNotEmpty()) {
            Text(
                text = "You feel $selectedMood today!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 26.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Question Of The Day Section
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Question of the Day",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
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
                    .padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

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
                    navController.navigate("home") {
                        popUpTo("landing") { inclusive = true } // removes the landing page from back stack

                    }
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0096C7)
            )
        ) {
            Text("Save answer")
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

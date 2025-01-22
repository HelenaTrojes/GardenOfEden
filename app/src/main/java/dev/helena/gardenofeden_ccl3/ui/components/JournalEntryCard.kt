package dev.helena.gardenofeden_ccl3.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity
import dev.helena.gardenofeden_ccl3.ui.theme.Rose
import dev.helena.gardenofeden_ccl3.ui.theme.Shit
import dev.helena.gardenofeden_ccl3.ui.viewmodel.EntryViewModel
import java.time.LocalDate

@Composable
fun JournalEntryCard(
    entry: EntryEntity,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onClick: () -> Unit,
) {

    val moodToEmojiMap = mapOf(
        "Happy" to "😊",
        "Sad" to "😢",
        "Surprised" to "😲",
        "Silly" to "😜",
        "Angry" to "😡"
    )

    val date = LocalDate.ofEpochDay(entry.date / 86400000L) // Convert timestamp to LocalDate
    val formattedDate = date.toString() // Format the date as needed

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
                println("Navigating to entry ID: ${entry.id}")
            }
    ) {
        Text("EntryID: ${entry.id}")

        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    color = Rose,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Shit,
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )

            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 30.dp)
                .padding(start = 5.dp)
        ) {
            // Question Text
            Text(
                text = "${entry.question} ",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier
                   .fillMaxWidth()
                    .padding(bottom = 4.dp),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // get the emoji for the selected mood
                val moodEmoji = moodToEmojiMap[entry.mood] ?: entry.mood

                // Mood of entry
                Text(
                    text = buildAnnotatedString {
                        append("Mood: ")
                        withStyle(style = SpanStyle(fontSize = 22.sp)) {
                            append(moodEmoji)
                        }
                    },
                   // text = "Mood: $moodEmoji",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )
                // Date Text
                Text(
                    text = "Date: $formattedDate ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                   modifier = Modifier.padding(top = 4.dp)
                )
            }


        }
    }
}
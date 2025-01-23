package dev.helena.gardenofeden_ccl3.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//only for the detail screen
@Composable
fun MoodSelector(selectedMood: String, onMoodSelected: (String) -> Unit) {
    val moodToEmojiMap = mapOf(
        "Happy" to "😊",
        "Sad" to "😢",
        "Surprised" to "😲",
        "Silly" to "😜",
        "Angry" to "😡"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        moodToEmojiMap.forEach { (mood, emoji) ->
            IconButton(onClick = { onMoodSelected(mood) }) {
                Text(
                    text = emoji,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}
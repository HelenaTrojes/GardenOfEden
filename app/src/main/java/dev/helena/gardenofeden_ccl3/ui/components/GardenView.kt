package dev.helena.gardenofeden_ccl3.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawAngryFlower
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawDefaultFlower
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawHappyFlower
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawSadFlower
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawSillyFlower
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawStem
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawSurprisedFlower
import kotlinx.coroutines.delay


@Composable
fun GardenView(entries: List<EntryEntity>, growthTriggered: Boolean) {
    val plants = remember(entries) {
        entries.map { entry ->
            mutableStateOf(
                Plant(
                    mood = entry.mood,
                    growthStage = PlantGrowthStage.SEED
                )
            )
        }
    }

    // Fixed height mapping for moods
    val moodStemHeights = mapOf(
        "Happy" to 200f,
        "Sad" to 120f,
        "Calm" to 180f,
        "Silly" to 160f,
        "Angry" to 140f,
        "Default" to 150f // Fallback for undefined moods
    )

    // Generate random positions for the flowers
    val positions = remember(entries) {
        entries.map {
            Offset(
                x = (90..1000).random().toFloat(), // Random horizontal placement
                y = (300..1400).random().toFloat() // Random vertical placement
            )
        }
    }

    // Animate growth if triggered
    LaunchedEffect(growthTriggered) {
        if (growthTriggered) {
            plants.forEach { plantState ->
                delay(500L) // Delay between each growth stage
                plantState.value = plantState.value.copy(growthStage = PlantGrowthStage.SPROUT)
                delay(600L)
                plantState.value = plantState.value.copy(growthStage = PlantGrowthStage.BLOOM)
            }
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        plants.forEachIndexed { index, plantState ->
            val position = positions[index]
            val baseStemHeight = moodStemHeights[plantState.value.mood] ?: moodStemHeights["Default"]!!
            val growthHeight = when (plantState.value.growthStage) {
                PlantGrowthStage.SEED -> baseStemHeight * 0.25f
                PlantGrowthStage.SPROUT -> baseStemHeight * 0.5f
                PlantGrowthStage.BLOOM -> baseStemHeight
            }

            drawStem(position.x, position.y, growthHeight)

            // Only draw the flower (petals) at BLOOM stage
            if (plantState.value.growthStage == PlantGrowthStage.BLOOM) {
                drawFlower(position.x, position.y - growthHeight, plantState.value.mood)
            }
        }
    }
}

fun DrawScope.drawFlower(centerX: Float, centerY: Float, mood: String) {
    when (mood) {
        "Happy" -> drawHappyFlower(centerX, centerY)
        "Sad" -> drawSadFlower(centerX, centerY)
        "Surprised" -> drawSurprisedFlower(centerX, centerY)
        "Silly" -> drawSillyFlower(centerX, centerY)
        "Angry" -> drawAngryFlower(centerX, centerY)
        else -> drawDefaultFlower(centerX, centerY)
    }
}

data class Plant(
    val mood: String,
    val growthStage: PlantGrowthStage
)

enum class PlantGrowthStage {
    SEED, SPROUT, BLOOM
}


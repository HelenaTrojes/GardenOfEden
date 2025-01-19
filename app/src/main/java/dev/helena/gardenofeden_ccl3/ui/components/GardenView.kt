package dev.helena.gardenofeden_ccl3.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity


@Composable
fun GardenView(entries: List<EntryEntity>, growthTriggered: Boolean) {
    // Map each entry to a plant with its initial growth stage as SEED
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

    // Trigger plant growth only once when growthTriggered becomes true
    if (growthTriggered) {
        plants.forEach { plantState ->
            plantState.value = plantState.value.copy(
                growthStage = when (plantState.value.growthStage) {
                    PlantGrowthStage.SEED -> PlantGrowthStage.SPROUT
                    PlantGrowthStage.SPROUT -> PlantGrowthStage.BLOOM
                    PlantGrowthStage.BLOOM -> PlantGrowthStage.BLOOM // Fully grown
                }
            )
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        plants.forEachIndexed { index, plantState ->
            val growthHeight = when (plantState.value.growthStage) {
                PlantGrowthStage.SEED -> 20f
                PlantGrowthStage.SPROUT -> 60f
                PlantGrowthStage.BLOOM -> 120f
            }

            drawLine(
                color = Color.Green,
                start = Offset(50f + (index * 100f), size.height),
                end = Offset(50f + (index * 100f), size.height - growthHeight),
                strokeWidth = 8f
            )
            drawCircle(
                color = getPlantColor(plantState.value.mood),
                radius = 20f,
                center = Offset(50f + (index * 100f), size.height - growthHeight - 10f)
            )
        }
    }
}
fun getPlantColor(mood: String): Color {
    return when (mood) {
        "Happy" -> Color.Yellow
        "Sad" -> Color.Blue
        "Surprised" -> Color.Magenta
        "Silly" -> Color.Red
        "Angry" -> Color.DarkGray
        else -> Color.Gray
    }
}

data class Plant(
    val mood: String,
    val growthStage: PlantGrowthStage
)

enum class PlantGrowthStage {
    SEED, SPROUT, BLOOM
}


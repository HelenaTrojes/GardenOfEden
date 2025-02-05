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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawAngryFlower
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawDefaultFlower
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawHappyFlower
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawSadFlower
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawSillyFlower
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawStem
import dev.helena.gardenofeden_ccl3.ui.components.flowerComponents.drawSurprisedFlower
import kotlinx.coroutines.delay
import kotlin.random.Random

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
        "Surprised" to 100f,
        "Silly" to 160f,
        "Angry" to 0f,
        "Default" to 150f // Fallback for undefined moods
    )

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

//    val density = LocalDensity.current.density

    // Convert to actual pixels inside the composable
    val screenWidthPx = with(LocalDensity.current) { screenWidthDp.toPx() }
    val screenHeightPx = with(LocalDensity.current) { screenHeightDp.toPx() }

    // Define garden area margins in pixels
    val marginX = screenWidthPx * 0.06f // 5% margin on each side
    val marginTop = screenHeightPx * 0.09f // Top margin stays the same
    val marginBottom = screenHeightPx * 0.275f // Reduce the bottom margin to make garden shorter

    // Define the garden height with reduced bottom margin
    val gardenHeight = screenHeightPx - marginTop - marginBottom // Shorter garden

    // Garden area width
    val gardenWidth = screenWidthPx - 2 * marginX

    // Calculate the number of columns and rows based on the available space and plant count
    val numPlants = entries.size
    val columns = kotlin.math.ceil(kotlin.math.sqrt(numPlants.toFloat())).toInt() // Number of columns based on sqrt of total plants
    val rows = kotlin.math.ceil(numPlants.toFloat() / columns).toInt() // Calculate rows based on the columns

    // Dynamically calculate the size of each grid cell
    val cellWidth = gardenWidth / columns
    val cellHeight = gardenHeight / rows

    // Generate positions with slight randomness
    val positions = remember(entries) {
        List(entries.size) { index ->
            val row = index / columns
            val col = index % columns

//            Offset(
//                x = marginX + col * cellWidth + Random.nextFloat() * cellWidth * 0.3f, // Add slight randomness
//                y = marginTop + row * cellHeight + Random.nextFloat() * cellHeight * 0.3f
//            )
            val x = marginX + col * cellWidth + Random.nextFloat() * cellWidth * 0.5f
            val y = marginTop + row * cellHeight + Random.nextFloat() * cellHeight * 0.5f

            // Debug: Print the position to see the values
//            println("Plant $index position: x = $x, y = $y")

            Offset(x, y)
        }
    }

    // Animate growth if triggered
    LaunchedEffect(growthTriggered) {
        if (growthTriggered) {
            plants.forEach { plantState ->
                plantState.value = plantState.value.copy(growthStage = PlantGrowthStage.SPROUT)
            }
            delay(600L)
            plants.forEach { plantState ->
                plantState.value = plantState.value.copy(growthStage = PlantGrowthStage.BLOOM)
            }
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Draw the red border for the garden area
//        drawRect(
//            color = androidx.compose.ui.graphics.Color.Red,
//            size = androidx.compose.ui.geometry.Size(gardenWidth, gardenHeight),
//            topLeft = Offset(marginX, marginTop), // This keeps the red border at the correct position
//            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f) // Border thickness
//        )


        // Draw the plants inside the garden area
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
                val flowerCenterY = position.y - growthHeight

                drawFlower(position.x, flowerCenterY, plantState.value.mood)
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

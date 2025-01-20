package dev.helena.gardenofeden_ccl3.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity
import dev.helena.gardenofeden_ccl3.ui.theme.BlueDark
import dev.helena.gardenofeden_ccl3.ui.theme.BlueLight
import dev.helena.gardenofeden_ccl3.ui.theme.CheekeyDark
import dev.helena.gardenofeden_ccl3.ui.theme.CheekyLight
import dev.helena.gardenofeden_ccl3.ui.theme.LemonDark
import dev.helena.gardenofeden_ccl3.ui.theme.LemonLight
import dev.helena.gardenofeden_ccl3.ui.theme.RoseLight
import dev.helena.gardenofeden_ccl3.ui.theme.VioletDark
import dev.helena.gardenofeden_ccl3.ui.theme.VioletLight
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun GardenView(entries: List<EntryEntity>, growthTriggered: Boolean, onPlantStroke: (String) -> Unit) {
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
                x = (50..350).random().toFloat(), // Random horizontal placement
                y = (300..900).random().toFloat() // Random vertical placement
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

                // Detect the stroke gesture
                Modifier.pointerInput(Unit) {
                    detectDragGestures { _, _ ->
                        // When stroked, trigger a random message
                        val messages = listOf(
                            "Thanks for taking care of me!",
                            "Could you please not get on my nerves?",
                            "I guess you really dig me!",
                            "Pet me, and I’ll bloom faster!",
                            "Don’t you have other plants to stroke?"
                        )
                        onPlantStroke(messages.random())
                    }
                }
            }
        }
    }
}



fun DrawScope.drawStem(x: Float, baseY: Float, height: Float) {
    drawLine(
        color = Color.Green,
        start = Offset(x, baseY),
        end = Offset(x, baseY - height),
        strokeWidth = 12f
    )
}

fun DrawScope.drawFlower(centerX: Float, centerY: Float, mood: String) {
    when (mood) {

                "Happy" -> drawHappyFlower(centerX, centerY)
                "Sad" -> drawSadFlower(centerX, centerY)
                "Calm" -> drawCactus(centerX, centerY)
                "Silly" -> drawSillyFlower(centerX, centerY)
                "Angry" -> drawAngryFlower(centerX, centerY)
                else -> drawDefaultFlower(centerX, centerY)
            }
        }

fun DrawScope.drawHappyFlower(centerX: Float, centerY: Float) {
    drawCircle(color = LemonDark, radius = 50f, center = Offset(centerX, centerY))
    repeat(6) { i ->
        val angle = Math.toRadians((i * 60).toDouble())
        val petalCenter = Offset(
            centerX + 70f * cos(angle).toFloat(),
            centerY + 70f * sin(angle).toFloat()
        )
        drawCircle(color = Color.Yellow.copy(alpha = 0.8f), radius = 30f, center = petalCenter)
    }
}

fun DrawScope.drawSadFlower(centerX: Float, centerY: Float) {
    drawCircle(color = BlueDark, radius = 40f, center = Offset(centerX, centerY))
    drawArc(
        color = Color.Blue.copy(alpha = 0.7f),
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = true,
        topLeft = Offset(centerX - 30f, centerY),
        size = Size(60f, 40f)
    )
}

fun DrawScope.drawCactus(centerX: Float, centerY: Float) {
    drawRect(
        color = Color(0xFF4CAF50),
        topLeft = Offset(centerX - 20f, centerY - 80f),
        size = Size(40f, 80f)
    )
    drawCircle(
        color = Color(0xFF388E3C),
        radius = 10f,
        center = Offset(centerX - 30f, centerY - 40f)
    )
    drawCircle(
        color = Color(0xFF388E3C),
        radius = 10f,
        center = Offset(centerX + 30f, centerY - 40f)
    )
}

fun DrawScope.drawSillyFlower(centerX: Float, centerY: Float) {
    drawCircle(color = CheekyLight, radius = 40f, center = Offset(centerX, centerY))
    drawCircle(color = LemonLight, radius = 15f, center = Offset(centerX - 15f, centerY - 10f))
    drawCircle(color = RoseLight, radius = 15f, center = Offset(centerX + 15f, centerY - 10f))
    drawCircle(color = VioletDark, radius = 5f, center = Offset(centerX - 15f, centerY - 10f))
    drawCircle(color = BlueDark, radius = 5f, center = Offset(centerX + 15f, centerY - 10f))
    drawArc(
        color = BlueLight,
        startAngle = 0f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(centerX - 20f, centerY + 10f),
        size = Size(40f, 20f)
    )
}

fun DrawScope.drawAngryFlower(centerX: Float, centerY: Float) {
    drawCircle(color = Color.DarkGray, radius = 50f, center = Offset(centerX, centerY))
    drawLine(
        color = CheekeyDark,
        start = Offset(centerX - 20f, centerY - 20f),
        end = Offset(centerX - 40f, centerY - 40f),
        strokeWidth = 5f
    )
    drawLine(
        color = CheekyLight,
        start = Offset(centerX + 20f, centerY - 20f),
        end = Offset(centerX + 40f, centerY - 40f),
        strokeWidth = 5f
    )
}

fun DrawScope.drawDefaultFlower(centerX: Float, centerY: Float) {
    drawCircle(color = VioletLight, radius = 40f, center = Offset(centerX, centerY))
}

data class Plant(
    val mood: String,
    val growthStage: PlantGrowthStage
)

enum class PlantGrowthStage {
    SEED, SPROUT, BLOOM
}


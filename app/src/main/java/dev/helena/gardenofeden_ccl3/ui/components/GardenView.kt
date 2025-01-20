package dev.helena.gardenofeden_ccl3.ui.components

import androidx.compose.foundation.Canvas
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
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity
import dev.helena.gardenofeden_ccl3.ui.theme.MintLeaf
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin


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
        drawHill(size)

        plants.forEachIndexed { index, plantState ->
            val plantOffsetX = 100f + (index * 150f) // Space plants evenly
            val growthHeight = when (plantState.value.growthStage) {
                PlantGrowthStage.SEED -> 40f
                PlantGrowthStage.SPROUT -> 80f
                PlantGrowthStage.BLOOM -> 160f
            }

            drawStem(plantOffsetX, size.height, growthHeight)

            // Only draw the flower (petals) at BLOOM stage
            if (plantState.value.growthStage == PlantGrowthStage.BLOOM) {
                drawFlower(plantOffsetX, size.height - growthHeight, plantState.value.mood)
            }
        }
    }
}
fun DrawScope.drawHill(size: Size) {
    drawArc(
        color = MintLeaf, // Light green for the hill
        startAngle = 0f,
        sweepAngle = 180f,
        useCenter = true,
        topLeft = Offset(-size.width * 0.5f, size.height * 0.5f),
        size = Size(size.width * 2f, size.height),
    )
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
    drawCircle(color = Color.Yellow, radius = 50f, center = Offset(centerX, centerY))
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
    drawCircle(color = Color.Blue, radius = 40f, center = Offset(centerX, centerY))
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
    drawCircle(color = Color.Red, radius = 40f, center = Offset(centerX, centerY))
    drawCircle(color = Color.White, radius = 15f, center = Offset(centerX - 15f, centerY - 10f))
    drawCircle(color = Color.White, radius = 15f, center = Offset(centerX + 15f, centerY - 10f))
    drawCircle(color = Color.Black, radius = 5f, center = Offset(centerX - 15f, centerY - 10f))
    drawCircle(color = Color.Black, radius = 5f, center = Offset(centerX + 15f, centerY - 10f))
    drawArc(
        color = Color.Black,
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
        color = Color.Black,
        start = Offset(centerX - 20f, centerY - 20f),
        end = Offset(centerX - 40f, centerY - 40f),
        strokeWidth = 5f
    )
    drawLine(
        color = Color.Black,
        start = Offset(centerX + 20f, centerY - 20f),
        end = Offset(centerX + 40f, centerY - 40f),
        strokeWidth = 5f
    )
}

fun DrawScope.drawDefaultFlower(centerX: Float, centerY: Float) {
    drawCircle(color = Color.Gray, radius = 40f, center = Offset(centerX, centerY))
}

data class Plant(
    val mood: String,
    val growthStage: PlantGrowthStage
)

enum class PlantGrowthStage {
    SEED, SPROUT, BLOOM
}


package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.helena.gardenofeden_ccl3.ui.theme.Green

fun DrawScope.drawSurprisedFlower(centerX: Float, centerY: Float) {

    // Leaves on the stem
    drawLine(
        color = Color(0xFF4CAF50), // Subdued green
        start = Offset(centerX - 10f, centerY - 60f),
        end = Offset(centerX - 20f, centerY - 80f), // Closer leaf
        strokeWidth = 4f
    )
    drawLine(
        color = Color(0xFF4CAF50), // Subdued green
        start = Offset(centerX + 10f, centerY - 60f),
        end = Offset(centerX + 20f, centerY - 80f), // Closer leaf
        strokeWidth = 4f
    )

    // Flower center (mouth-like oval for surprise expression)
    drawOval(
        color = Color(0xFFFFD54F), // Softer yellow
        topLeft = Offset(centerX - 25f, centerY - 130f), // Slightly smaller
        size = Size(50f, 70f)
    )

    // Eyes (wide-open circles)
    drawCircle(
        color = Color.White,
        radius = 12f, // Slightly smaller eyes
        center = Offset(centerX - 15f, centerY - 170f)
    )
    drawCircle(
        color = Color.White,
        radius = 12f,
        center = Offset(centerX + 15f, centerY - 170f)
    )
    drawCircle(
        color = Color.Black,
        radius = 5f, // Slightly smaller pupils
        center = Offset(centerX - 15f, centerY - 170f)
    )
    drawCircle(
        color = Color.Black,
        radius = 5f,
        center = Offset(centerX + 15f, centerY - 170f)
    )

    // Petals (closer to the center, with muted colors)
    val petalColors = listOf(
        Color(0xFFE57373), // Muted red
        Color(0xFFCE93D8), // Muted magenta
        Color(0xFFB3E5FC)  // Muted cyan
    )
    for (i in 0..5) {
        val angle = Math.toRadians((i * 60).toDouble()).toFloat()
        val petalX = centerX + 50f * kotlin.math.cos(angle) // Closer petals
        val petalY = centerY - 130f + 50f * kotlin.math.sin(angle)
        drawOval(
            color = petalColors[i % petalColors.size],
            topLeft = Offset(petalX - 15f, petalY - 20f), // Smaller petals
            size = Size(30f, 50f)
        )
    }
}

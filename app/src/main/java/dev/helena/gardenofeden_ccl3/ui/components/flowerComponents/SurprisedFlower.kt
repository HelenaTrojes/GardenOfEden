package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.helena.gardenofeden_ccl3.ui.theme.Green

fun DrawScope.drawSurprisedFlower(centerX: Float, centerY: Float) {
    // Adjusted centerY for flower to align with the stem
    val flowerCenterY = centerY - 30f

    // Leaves on the stem
    drawLine(
        color = Color(0xFF4CAF50), //green
        start = Offset(centerX - 10f, centerY - 60f),
        end = Offset(centerX - 20f, centerY - 80f),
        strokeWidth = 4f
    )
    drawLine(
        color = Color(0xFF4CAF50), //green
        start = Offset(centerX + 10f, centerY - 60f),
        end = Offset(centerX + 20f, centerY - 80f),
        strokeWidth = 4f
    )

    // mouth
    drawOval(
        color = Color(0xFFFFD54F), // Softer yellow
        topLeft = Offset(centerX - 25f, flowerCenterY - 35f),
        size = Size(50f, 70f)
    )

    // Eyes (wide-open circles)
    drawCircle(
        color = Color.White,
        radius = 16f, // Larger eyes
        center = Offset(centerX - 15f, flowerCenterY - 75f)
    )
    drawCircle(
        color = Color.White,
        radius = 16f,
        center = Offset(centerX + 15f, flowerCenterY - 75f)
    )
    drawCircle(
        color = Color.Black,
        radius = 6f,
        center = Offset(centerX - 15f, flowerCenterY - 75f)
    )
    drawCircle(
        color = Color.Black,
        radius = 6f,
        center = Offset(centerX + 15f, flowerCenterY - 75f)
    )

    // Petals
    val petalColors = listOf(
        Color(0xFFFF4081), // Bright pink
        Color(0xFF64B5F6), // Bright blue
        Color(0xFF81C784)  // Bright green
    )
    for (i in 0..5) {
        val angle = Math.toRadians((i * 60).toDouble()).toFloat()
        val petalX = centerX + 50f * kotlin.math.cos(angle)
        val petalY = flowerCenterY + 50f * kotlin.math.sin(angle)
        drawOval(
            color = petalColors[i % petalColors.size],
            topLeft = Offset(petalX - 20f, petalY - 20f),
            size = Size(40f, 60f)
        )
    }

    // Larger round mouth for surprise
    drawCircle(
        color = Color.Black,
        radius = 20f, // Larger mouth
        center = Offset(centerX, flowerCenterY - 50f) // Position mouth
    )

    //sparkles
    for (i in 0..5) {
        val angle = Math.toRadians((i * 60 + 30).toDouble()).toFloat()
        val sparkleX = centerX + 70f * kotlin.math.cos(angle)
        val sparkleY = flowerCenterY + 70f * kotlin.math.sin(angle)
        drawCircle(
            color = Color.Yellow,
            radius = 5f,
            center = Offset(sparkleX, sparkleY)
        )
    }
}
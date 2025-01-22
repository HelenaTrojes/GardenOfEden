package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.helena.gardenofeden_ccl3.ui.theme.Green

fun DrawScope.drawSurprisedFlower(centerX: Float, centerY: Float) {
    // Stem
    drawRect(
        color = Color.Green,
        size = Size(20f, 120f),
        topLeft = Offset(centerX - 10f, centerY - 60f)
    )

    // Leaves on the stem
    drawLine(
        color = Color.Green,
        start = Offset(centerX - 10f, centerY - 60f), // Leaf starting point near stalk
        end = Offset(centerX - 30f, centerY - 90f), // Leaf extending out
        strokeWidth = 5f
    )
    drawLine(
        color = Color.Green,
        start = Offset(centerX + 10f, centerY - 60f), // Leaf starting point on the other side
        end = Offset(centerX + 30f, centerY - 90f), // Leaf extending outward
        strokeWidth = 5f
    )

    // Flower center (mouth-like oval for surprise expression)
    drawOval(
        color = Color.Yellow,
        topLeft = Offset(centerX - 30f, centerY - 140f),
        size = Size(60f, 80f)
    )

    // Eyes (wide-open circles)
    drawCircle(
        color = Color.White,
        radius = 15f,
        center = Offset(centerX - 20f, centerY - 180f)
    )
    drawCircle(
        color = Color.White,
        radius = 15f,
        center = Offset(centerX + 20f, centerY - 180f)
    )
    drawCircle(
        color = Color.Black,
        radius = 7f,
        center = Offset(centerX - 20f, centerY - 180f)
    )
    drawCircle(
        color = Color.Black,
        radius = 7f,
        center = Offset(centerX + 20f, centerY - 180f)
    )

    // Petals (radiating outward like a burst of surprise)
    val petalColors = listOf(Color.Red, Color.Magenta, Color.Cyan)
    for (i in 0..5) {
        val angle = Math.toRadians((i * 60).toDouble()).toFloat()
        val petalX = centerX + 70f * kotlin.math.cos(angle)
        val petalY = centerY - 140f + 70f * kotlin.math.sin(angle)
        drawOval(
            color = petalColors[i % petalColors.size],
            topLeft = Offset(petalX - 20f, petalY - 30f),
            size = Size(40f, 60f)
        )
    }
}

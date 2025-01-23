package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.helena.gardenofeden_ccl3.ui.theme.BlueDark
import dev.helena.gardenofeden_ccl3.ui.theme.BlueLight
import dev.helena.gardenofeden_ccl3.ui.theme.Green
import kotlin.math.cos
import kotlin.math.sin


fun DrawScope.drawSadFlower(centerX: Float, centerY: Float) {
    val petalCount = 8
    val petalOffset = 50f

// leaves
    drawLine(
        color = Green,
        start = Offset(centerX - 20f, centerY + 50f),
        end = Offset(centerX + 10f, centerY + 80f),
        strokeWidth = 6f
    )
    drawCircle(color = BlueDark, radius = 40f, center = Offset(centerX, centerY))

    //   "bell-shaped" petal around the center
    repeat(petalCount) { i ->
        val angle = Math.toRadians((i * 360 / petalCount).toDouble())
        val petalX = centerX + petalOffset * cos(angle).toFloat()
        val petalY = centerY + petalOffset * sin(angle).toFloat()

        drawOval(
            color = BlueLight.copy(alpha = 0.8f),  // for petals
            size = Size(50f, 60f),
            topLeft = Offset(petalX - 25f, petalY - 30f)
        )
    }

    //sad face inside the flower
    val faceRadius = 18f
    drawCircle(color = Color.White, radius = faceRadius, center = Offset(centerX, centerY))

    //Eyes
    drawCircle(color = Color.Black, radius = 3f, center = Offset(centerX - 7f, centerY - 7f))  // Left eye
    drawCircle(color = Color.Black, radius = 3f, center = Offset(centerX + 7f, centerY - 7f))  // Right eye

    //Sad mouth
    drawArc(
        color = Color.Black,
        startAngle = 10f,
        sweepAngle = -180f,
        useCenter = false,
        topLeft = Offset(centerX - 9f, centerY + 5f),
        size = Size(18f, 12f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
    )


}

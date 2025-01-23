package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.drawHappyFlower(centerX: Float, centerY: Float) {
    // center of the flower (yellow)
    drawCircle(color = Color(0xFFFFEB3B), radius = 50f, center = Offset(centerX, centerY))

    // background petals
    for (i in 0..5) {
        val angle = Math.toRadians((i * 60).toDouble())
        val petalX = centerX + 80 * cos(angle).toFloat()
        val petalY = centerY + 80 * sin(angle).toFloat()
        drawCircle(color = Color(0xFFFFA000), radius = 35f, center = Offset(petalX, petalY))  // Slightly smaller background petals
    }

    //  the main petals in the foreground
    for (i in 0..5) {
        val angle = Math.toRadians((i * 60).toDouble())
        val petalX = centerX + 60 * cos(angle).toFloat()  // Slightly smaller distance for main petals
        val petalY = centerY + 60 * sin(angle).toFloat()  // Slightly smaller distance for main petals
        drawCircle(color = Color(0xFFFFC107), radius = 25f, center = Offset(petalX, petalY))  // Slightly smaller main petals
    }

    // few smaller petals
    drawCircle(color = Color(0xFFFFD54F), radius = 15f, center = Offset(centerX - 40f, centerY - 70f))  // Smaller accent petals
    drawCircle(color = Color(0xFFFFD54F), radius = 15f, center = Offset(centerX + 40f, centerY - 70f))  // Smaller accent petals

    val stemMidY = centerY + 110f  // leaves slightly below the flower's center
    drawLeaf(centerX - 40f, stemMidY)  // Left leaf in the middle
    drawLeaf(centerX + 30f, stemMidY)  // Right leaf in the middle
}

fun DrawScope.drawLeaf(x: Float, y: Float) {
    // leave
    drawOval(
        color = Color(0xFF388E3C),
        size = Size(40f, 20f),
        topLeft = Offset(x - 20f, y - 10f)  // Position the leaf near stem
    )
}


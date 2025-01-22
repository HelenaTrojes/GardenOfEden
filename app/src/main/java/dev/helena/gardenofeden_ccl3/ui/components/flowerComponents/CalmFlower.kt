package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.helena.gardenofeden_ccl3.ui.theme.Green

fun DrawScope.drawCactus(centerX: Float, centerY: Float) {
    drawRect(
        color = Color.Green,
        size = Size(20f, 120f),
        topLeft = Offset(centerX - 10f, centerY - 60f)
    )

    // bamboo leaves
    drawLine(
        color = Green,
        start = Offset(centerX - 10f, centerY - 60f), // Leaf starting point near stalk
        end = Offset(centerX - 30f, centerY - 90f),  // Leaf extending out
        strokeWidth = 5f
    )
    drawLine(
        color = Green,
        start = Offset(centerX + 10f, centerY - 60f), // Leaf starting point on the other side
        end = Offset(centerX + 30f, centerY - 90f),  // Leaf extending outward
        strokeWidth = 5f
    )

    //second stock
    drawRect(
        color = Color.Green,
        size = Size(20f, 120f),
        topLeft = Offset(centerX - 60f, centerY - 60f)
    )

    // leaves
    drawLine(
        color = Green,
        start = Offset(centerX - 60f, centerY - 60f),
        end = Offset(centerX - 80f, centerY - 90f),
        strokeWidth = 5f
    )
    drawLine(
        color = Color.Green,
        start = Offset(centerX - 40f, centerY - 60f),
        end = Offset(centerX - 60f, centerY - 90f),
        strokeWidth = 5f
    )
}
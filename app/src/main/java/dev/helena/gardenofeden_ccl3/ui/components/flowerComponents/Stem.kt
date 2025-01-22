package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.helena.gardenofeden_ccl3.ui.theme.Green


fun DrawScope.drawStem(x: Float, baseY: Float, height: Float) {
    drawLine(
        color = Green,
        start = Offset(x, baseY),
        end = Offset(x, baseY - height),
        strokeWidth = 12f
    )
}
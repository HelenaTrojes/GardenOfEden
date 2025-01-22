package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.helena.gardenofeden_ccl3.ui.theme.VioletLight


fun DrawScope.drawDefaultFlower(centerX: Float, centerY: Float) {
    drawCircle(color = VioletLight, radius = 40f, center = Offset(centerX, centerY))
}

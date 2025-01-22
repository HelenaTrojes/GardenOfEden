package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.helena.gardenofeden_ccl3.ui.theme.Green

fun DrawScope.drawAngryFlower(centerX: Float, centerY: Float) {

    // cactus body
    drawRoundRect(
        color = Green,
        size = Size(80f, 160f),
        topLeft = Offset(centerX - 40f, centerY - 80f),
        cornerRadius = CornerRadius.Zero
    )

    // side branches
    drawRoundRect(
        color = Green,
        size = Size(40f, 80f),
        topLeft = Offset(centerX - 50f, centerY - 50f),
        cornerRadius = CornerRadius.Zero
    )
    drawRoundRect(
        color = Green,
        size = Size(40f, 80f),
        topLeft = Offset(centerX + 10f, centerY - 50f),
        cornerRadius = CornerRadius.Zero
    )

    // spikes kinda
    drawLine(
        color = Color.DarkGray,
        start = Offset(centerX - 20f, centerY - 60f),
        end = Offset(centerX - 40f, centerY - 80f),
        strokeWidth = 5f
    )
    drawLine(
        color = Color.DarkGray,
        start = Offset(centerX + 20f, centerY - 60f),
        end = Offset(centerX + 40f, centerY - 80f),
        strokeWidth = 5f
    )
    drawLine(
        color = Color.DarkGray,
        start = Offset(centerX - 30f, centerY),
        end = Offset(centerX - 50f, centerY + 20f),
        strokeWidth = 5f
    )
    drawLine(
        color = Color.DarkGray,
        start = Offset(centerX + 30f, centerY),
        end = Offset(centerX + 50f, centerY + 20f),
        strokeWidth = 5f
    )

    // angry eyes
    drawCircle(
        color = Color.Black,
        radius = 10f,
        center = Offset(centerX - 15f, centerY - 40f)
    )
    drawCircle(
        color = Color.Black,
        radius = 10f,
        center = Offset(centerX + 15f, centerY - 40f)
    )

    // Angry eyebrows
    drawLine(
        color = Color.Black,
        start = Offset(centerX - 20f, centerY - 55f),
        end = Offset(centerX - 5f, centerY - 50f),
        strokeWidth = 3f
    )
    drawLine(
        color = Color.Black,
        start = Offset(centerX + 20f, centerY - 55f),
        end = Offset(centerX + 5f, centerY - 50f),
        strokeWidth = 3f
    )

    // angry mouth
    drawArc(
        color = Color.Black,
        startAngle = 0f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(centerX - 25f, centerY + 30f),
        size = Size(50f, 30f)
    )
}

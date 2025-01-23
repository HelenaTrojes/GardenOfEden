package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path

fun DrawScope.drawAngryFlower(centerX: Float, centerY: Float) {
    // Cactus body
    drawRoundRect(
        color = Color(0xFF388E3C),
        size = Size(70f, 160f),
        topLeft = Offset(centerX - 40f, centerY - 80f),
        cornerRadius = CornerRadius(2f) // Rounded corners
    )

    // Cactus head
    val headPath = Path().apply {
        moveTo(centerX - 40f, centerY - 80f) // Start at the top of the cactus
        lineTo(centerX + 30f, centerY - 80f) // Top line
        lineTo(centerX + 26f, centerY - 120f) // Right top point
        lineTo(centerX - 30f, centerY - 120f) // Left top point
        lineTo(centerX - 40f, centerY - 80f) // Close connection with body
        close()
    }
    drawPath(headPath, Brush.verticalGradient(
        colors = listOf(Color(0xFF388E3C), Color(0xFFF33232)),
        startY = centerY - 70f,
        endY = centerY - 160f
    ))

// Side branches
    drawRoundRect(
        color = Color(0xFF388E3C),
        size = Size(40f, 80f),
        topLeft = Offset(centerX - 50f, centerY - 40f),
        cornerRadius = CornerRadius(20f)
    )
    drawRoundRect(
        color = Color(0xFF388E3C),
        size = Size(40f, 80f),
        topLeft = Offset(centerX + 10f, centerY - 40f),
        cornerRadius = CornerRadius(20f)
    )


    // Spikes
    // First spike (top-left)
    drawLine(
        color = Color(0xFF226B23),
        start = Offset(centerX - 30f, centerY - 40f),
        end = Offset(centerX - 45f, centerY - 60f),
        strokeWidth = 5f
    )

    // First spike (top-right)
    drawLine(
        color = Color(0xFF226B23),
        start = Offset(centerX + 30f, centerY - 40f),
        end = Offset(centerX + 45f, centerY - 60f),
        strokeWidth = 5f
    )

    // Second spike (middle-left)
    drawLine(
        color = Color(0xFF226B23),
        start = Offset(centerX - 40f, centerY + 10f),
        end = Offset(centerX - 60f, centerY + 20f),
        strokeWidth = 5f
    )

// Second spike (middle-right)
    drawLine(
        color = Color(0xFF226B23),
        start = Offset(centerX + 40f, centerY + 10f),
        end = Offset(centerX + 60f, centerY + 20f),
        strokeWidth = 5f
    )

// Angry eyes
    drawCircle(
        color = Color.Black,
        radius = 12f,
        center = Offset(centerX - 15f, centerY - 50f)
    )
    drawCircle(
        color = Color.Black,
        radius = 12f,
        center = Offset(centerX + 15f, centerY - 50f)
    )

// Angry eyebrows
    drawLine(
        color = Color.Black,
        start = Offset(centerX - 20f, centerY - 75f),
        end = Offset(centerX - 5f, centerY - 65f),
        strokeWidth = 4f // Thicker eyebrows
    )
    drawLine(
        color = Color.Black,
        start = Offset(centerX + 20f, centerY - 75f),
        end = Offset(centerX + 5f, centerY - 65f),
        strokeWidth = 4f
    )

    ///Angry Mouth
    drawLine(
        color = Color.Black,
        start = Offset(centerX - 20f, centerY + 10f), // Starting point of the line
        end = Offset(centerX + 20f, centerY + 10f),   // Ending point of the line
        strokeWidth = 5f
    )
}
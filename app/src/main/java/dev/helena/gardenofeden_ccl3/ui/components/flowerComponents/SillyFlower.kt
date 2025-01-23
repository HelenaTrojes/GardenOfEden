package dev.helena.gardenofeden_ccl3.ui.components.flowerComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import dev.helena.gardenofeden_ccl3.ui.theme.BlueDark
import dev.helena.gardenofeden_ccl3.ui.theme.BlueLight
import dev.helena.gardenofeden_ccl3.ui.theme.CheekyLight
import dev.helena.gardenofeden_ccl3.ui.theme.LemonLight
import dev.helena.gardenofeden_ccl3.ui.theme.Rose
import dev.helena.gardenofeden_ccl3.ui.theme.RoseLight
import dev.helena.gardenofeden_ccl3.ui.theme.VioletDark

fun DrawScope.drawSillyFlower(centerX: Float, centerY: Float) {
    // face
    drawCircle(color = CheekyLight, radius = 40f, center = Offset(centerX, centerY))
    drawCircle(color = LemonLight, radius = 15f, center = Offset(centerX - 15f, centerY - 10f))
    drawCircle(color = RoseLight, radius = 15f, center = Offset(centerX + 15f, centerY - 10f))
    drawCircle(color = VioletDark, radius = 5f, center = Offset(centerX - 15f, centerY - 10f))
    drawCircle(color = BlueDark, radius = 5f, center = Offset(centerX + 15f, centerY - 10f))

    //smile
    drawArc(
        color = BlueLight,
        startAngle = 0f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(centerX - 20f, centerY + 10f),
        size = Size(40f, 20f)
    )

    //hair
    drawLine(
        color = Rose,
        start = Offset(centerX - 30f, centerY - 40f),
        end = Offset(centerX - 50f, centerY - 60f),
        strokeWidth = 5f
    )
    drawLine(
        color = Rose,
        start = Offset(centerX + 30f, centerY - 40f),
        end = Offset(centerX + 50f, centerY - 60f),
        strokeWidth = 5f
    )
    drawLine(
        color = Rose,
        start = Offset(centerX - 10f, centerY - 50f),
        end = Offset(centerX - 20f, centerY - 70f),
        strokeWidth = 5f
    )
    drawLine(
        color = Rose,
        start = Offset(centerX + 10f, centerY - 50f),
        end = Offset(centerX + 20f, centerY - 70f),
        strokeWidth = 5f
    )

    //funny leaves
    withTransform({
        rotate(degrees = 45f, pivot = Offset(centerX - 60f, centerY + 50f))
    }) {
        drawOval(
            color = Green,
            topLeft = Offset(centerX - 60f, centerY + 50f),
            size = Size(80f, 40f)
        )
    }

    withTransform({
        rotate(degrees = -30f, pivot = Offset(centerX + 30f, centerY + 50f))
    }) {
        drawOval(
            color = Green,
            topLeft = Offset(centerX + 30f, centerY + 50f),
            size = Size(80f, 40f)
        )
    }

    // random angles leaves
    withTransform({
        rotate(degrees = 20f, pivot = Offset(centerX - 50f, centerY + 90f))
    }) {
        drawOval(
            color = Green,
            topLeft = Offset(centerX - 20f, centerY + 60f),
            size = Size(60f, 30f)
        )
    }

    withTransform({
        rotate(degrees = -20f, pivot = Offset(centerX + 20f, centerY + 90f))
    }) {
        drawOval(
            color = Green,
            topLeft = Offset(centerX + 20f, centerY + 90f),
            size = Size(60f, 30f)
        )
    }
}
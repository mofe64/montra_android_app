package com.nubari.montra.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.nubari.montra.general.components.util.generateSpendingLineGraphFillPath
import com.nubari.montra.general.components.util.generateSpendingLineGraphStrokePath
import com.nubari.montra.ui.theme.violet
import com.nubari.montra.ui.theme.violet100

@Composable
fun SpendingFrequencyCanvas(
    data: List<Double>,
    modifier: Modifier,
    pathColor: Color = violet100,
    fillColor: Color = violet
) {
    val pathProgress = remember {
        Animatable(initialValue = 0f)
    }
    val pathVisibility = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(Unit) {
        pathProgress.animateTo(
            1f, animationSpec = tween(1000)
        )
        pathVisibility.animateTo(
            targetValue = 1f, animationSpec = tween(1300)
        )
    }
    Canvas(
        modifier = modifier
            .fillMaxWidth()
    ) {

        drawPath(
            path = generateSpendingLineGraphStrokePath(
                size = size,
                spendingData = data,
                animationControlValue = pathProgress.value
            ),
            color = pathColor,
            style = Stroke(15f)
        )
        drawPath(
            path = generateSpendingLineGraphFillPath(
                size = size,
                spendingData = data,
            ),
            brush = Brush.verticalGradient(
                colors = listOf(
                    fillColor.copy(
                        alpha = 0.40f
                    ),
                    fillColor.copy(
                        alpha = 0.01f
                    )
                ),
            ),
            alpha = pathVisibility.value
        )


    }
}
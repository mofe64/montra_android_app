package com.nubari.montra.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.nubari.montra.general.components.util.generateSpendingLineGraphFillPath
import com.nubari.montra.general.components.util.generateSpendingLineGraphStrokePath
import com.nubari.montra.ui.theme.violet
import com.nubari.montra.ui.theme.violet100
import com.nubari.montra.ui.theme.violet20

@Composable
fun SpendingFrequencyCanvas(
    data: List<Double>,
    modifier: Modifier,
    pathColor: Color = violet100,
    fillColor: Color = violet
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
    ) {

        drawPath(
            path = generateSpendingLineGraphStrokePath(
                size = size,
                spendingData = data
            ),
            color = pathColor,
            style = Stroke(15f)
        )
        drawPath(
            path = generateSpendingLineGraphFillPath(
                size = size,
                spendingData = data
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
            )
        )


    }
}
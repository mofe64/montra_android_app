package com.nubari.montra.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.nubari.montra.general.components.generateSpendingLineGraphFillPath
import com.nubari.montra.general.components.generateSpendingLineGraphStrokePath
import com.nubari.montra.ui.theme.violet
import com.nubari.montra.ui.theme.violet100

@Composable
fun SpendingFrequencyCanvas(
    data: List<Double>,
    modifier: Modifier
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
            color = violet100,
            style = Stroke(15f)
        )
        drawPath(
            path = generateSpendingLineGraphFillPath(
                size = size,
                spendingData = data
            ),
            brush = Brush.verticalGradient(
                colors = listOf(
                    violet.copy(
                        alpha = 0.40f
                    ),
                    violet.copy(
                        alpha = 0.01f
                    )
                ),
            )
        )


    }
}
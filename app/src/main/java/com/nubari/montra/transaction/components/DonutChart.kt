package com.nubari.montra.transaction.components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*

import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.atan2

import kotlin.math.min


@Composable
fun DonutChart(
    modifier: Modifier,
    data: List<Float>,
    colors: List<Color>,
//    updateActiveSegmentIndex: (Int) -> Unit
) {

    if (data.isEmpty()) return

    val total = data.sum()
    val proportions = data.map {
        it * 100 / total
    }
    val percentages = proportions.map {
        360 * it / 100
    }


    var startAngle = 270f

//    var activeSegment by remember {
//        mutableStateOf(-1)
//    }
    val segmentSizes = mutableListOf<Float>()
    segmentSizes.add(percentages.first())

    for (x in 1 until percentages.size)
        segmentSizes.add(percentages[x] + segmentSizes[x - 1])


    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        val sideSize = min(constraints.maxWidth, constraints.maxHeight)
        Log.i("donut-chart-mw", constraints.maxWidth.toString())
        Log.i("donut-chart-mh", constraints.maxHeight.toString())
        Log.i("donut-chart-side-size", sideSize.toString())

        val pathPortion = remember {
            Animatable(initialValue = 0f)
        }
        LaunchedEffect(key1 = true) {
            pathPortion.animateTo(
                1f, animationSpec = tween(1000)
            )
        }


        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(sideSize.dp)
                .align(Alignment.Center)
                .clickable { }
//                .pointerInput(true) {
//                    detectTapGestures(
//                        onTap = {
//                            val clickedAngle = convertTouchEventPointToAngle(
//                                sideSize.toFloat(),
//                                sideSize.toFloat(),
//                                it.x,
//                                it.y
//                            )
//                            percentages.forEachIndexed { index, percentage ->
//                                if (clickedAngle <= percentage) {
//                                    if (activeSegment != index) {
//                                        activeSegment = index
//                                        updateActiveSegmentIndex(index)
//                                    }
//                                    return@detectTapGestures
//                                }
//                            }
//                        }
//                    )
//                }
        ) {
            val arcRadius = sideSize.toFloat()
            val cw = size.width
            val ch = size.height
            percentages.forEachIndexed { index, arcProgress ->

                drawArc(
                    color = colors[index],
                    startAngle = startAngle,
                    sweepAngle = arcProgress * pathPortion.value,
                    useCenter = false,
                    size = Size(arcRadius, arcRadius),
                    style = Stroke(
                        width = 60f,
                        cap = StrokeCap.Round,
                    ),
                    topLeft = Offset(
                        (cw / 2) - (arcRadius / 2),
                        (ch / 2) - (arcRadius / 2)
                    )
                )
                startAngle += arcProgress
            }

        }
    }

}

//private fun convertTouchEventPointToAngle(
//    width: Float,
//    height: Float,
//    xPos: Float,
//    yPos: Float
//): Double {
//    val x = xPos - (width * 0.5f)
//    val y = yPos - (height * 0.5f)
//
//    var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble()) + Math.PI / 2)
//    angle = if (angle < 0) angle + 360 else angle
//    return angle
//}




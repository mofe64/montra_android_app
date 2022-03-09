package com.nubari.montra.general.components.util


import android.graphics.PointF
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

fun generateSpendingLineGraphStrokePath(
    size: Size,
    spendingData: List<Double>,
    animationControlValue: Float
): Path {
    // determine distance between points on graph
    val distance = size.width / (spendingData.size + 1)
    var pointX = 0f
    val maxValue = (spendingData.maxOrNull() ?: 0).toDouble()
    val points = mutableListOf<PointF>()
    //set start point of graph to left edge of canvas
    points.add(PointF(pointX, size.height))
    // loop
    spendingData.forEachIndexed { index, data ->
        if (spendingData.size >= index + 1) {
            val y0 = (maxValue - data) * (size.height / maxValue)
            val x0 = pointX + distance
            points.add(PointF(x0, y0.toFloat()))
            pointX += distance
        }
    }
    //set end point of graph to right edge of canvas
    points.add(PointF(size.width, size.height))

    //define connections points to build the curve
    val conPoint1 = mutableListOf<PointF>()
    val conPoint2 = mutableListOf<PointF>()

    for (i in 1 until points.size) {
        conPoint1.add(
            PointF(
                (points[i].x + points[i - 1].x) / 2,
                points[i - 1].y
            )
        )
        conPoint2.add(
            PointF(
                (points[i].x + points[i - 1].x) / 2,
                points[i].y
            )
        )
    }
    val path = Path()
    path.moveTo(points.first().x * animationControlValue, points.first().y * animationControlValue)

    for (i in 1 until points.size) {
        path.cubicTo(
            conPoint1[i - 1].x * animationControlValue,
            conPoint1[i - 1].y * animationControlValue,
            conPoint2[i - 1].x * animationControlValue,
            conPoint2[i - 1].y * animationControlValue,
            points[i].x * animationControlValue,
            points[i].y * animationControlValue
        )
    }
    return path
}

fun generateSpendingLineGraphFillPath(
    size: Size,
    spendingData: List<Double>,
    animationControlValue: Float = 1f
): Path {
    return Path().apply {
        val distance = size.width / (spendingData.size + 1)
        var currentX = 0f
        val max = (spendingData.maxOrNull() ?: 0).toDouble()
        val points = mutableListOf<PointF>()
        //set start point of path to left edge of canvas
        points.add(PointF(0f, size.height))
        spendingData.forEachIndexed { index, data ->
            if (spendingData.size >= index + 1) {
                val y0 = (max - data).toFloat() * (size.height / max).toFloat()
                val x0 = currentX + distance
                points.add(PointF(x0, y0.toFloat()))
                currentX += distance
            }
        }
        //set end point of path to right edge of canvas
        points.add(PointF(size.width, size.height))
        val conPoint1 = mutableListOf<PointF>()
        val conPoint2 = mutableListOf<PointF>()

        for (i in 1 until points.size) {
            conPoint1.add(
                PointF(
                    (points[i].x + points[i - 1].x) / 2,
                    points[i - 1].y
                )
            )
            conPoint2.add(
                PointF(
                    (points[i].x + points[i - 1].x) / 2,
                    points[i].y
                )
            )
        }
        moveTo(points.first().x * animationControlValue, points.first().y * animationControlValue)
        for (i in 1 until points.size) {
            cubicTo(
                conPoint1[i - 1].x * animationControlValue,
                conPoint1[i - 1].y * animationControlValue,
                conPoint2[i - 1].x * animationControlValue,
                conPoint2[i - 1].y * animationControlValue,
                points[i].x * animationControlValue,
                points[i].y* animationControlValue
            )
        }
        lineTo(size.width + 40f, size.height + 40f)
        lineTo(-40f, size.height + 40f)
        close()

    }
}

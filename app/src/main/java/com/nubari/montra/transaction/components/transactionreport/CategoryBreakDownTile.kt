package com.nubari.montra.transaction.components.transactionreport

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.data.models.CategoryBreakdown
import com.nubari.montra.ui.theme.green100
import com.nubari.montra.ui.theme.light60
import com.nubari.montra.ui.theme.red100

private const val tag = "Category-break-down-tile"

@SuppressLint("LongLogTag")
@Composable
fun CategoryBreakDownTile(
    color: Color,
    breakdown: CategoryBreakdown,
    isPositive: Boolean = true
) {
    Log.i("$tag-breakdown", breakdown.toString())
    val progress = remember { Animatable(0f) }
    val progressDestination = breakdown.categoryPercentage / 100f
    LaunchedEffect(Unit) {
        progress.animateTo(
            progressDestination,
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(modifier = Modifier.padding(start = 5.dp)) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color)
                        .height(20.dp)
                        .width(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = breakdown.categoryName,
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp
                )
            }

            val symbol = if (isPositive) {
                "+"
            } else {
                "-"
            }
            Text(
                text = "$symbol ₦${breakdown.categoryAmount.toPlainString()}",
                fontSize = 24.sp,
                color = if (isPositive) {
                    green100
                } else {
                    red100
                },
                fontWeight = FontWeight(500)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        LinearProgressIndicator(
            color = color,
            progress = progress.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(40)),
            backgroundColor = light60
        )
    }
}
package com.nubari.montra.transaction.components.transactionreport

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.transaction.screens.colors
import com.nubari.montra.ui.theme.red100

@Composable
fun CategoryTile(
    color: Color = colors.random()
) {
    val progress = remember { Animatable(0f) }
    val y = listOf(.7f, .4f, .9f, .8f, .1f, .34f)
    LaunchedEffect(Unit) {
        progress.animateTo(
            y.random(),
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
            Row {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color)
                        .height(20.dp)
                        .width(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Shopping")
            }
            Text(text = "- ₦2000", fontSize = 20.sp, color = red100)
        }
        Spacer(modifier = Modifier.height(5.dp))
        LinearProgressIndicator(
            color = color,
            progress = progress.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(40))
        )
    }
}
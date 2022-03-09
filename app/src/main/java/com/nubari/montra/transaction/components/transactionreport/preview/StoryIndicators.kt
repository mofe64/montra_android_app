package com.nubari.montra.transaction.components.transactionreport.preview

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun StoryIndicators(
    modifier: Modifier = Modifier,
    stepCount: Int,
    stepDuration: Int,
    unSelectedColor: Color,
    selectedColor: Color,
    currentStep: Int,
    onStepChanged: (Int) -> Unit,
    isPaused: Boolean = false,
    onComplete: () -> Unit,
) {
    val currentStepState = remember(currentStep) { mutableStateOf(currentStep) }
    val progress = remember(currentStep) { Animatable(0f) }

    Row(
        modifier = modifier
    ) {
        for (i in 0 until stepCount) {
            val stepProgress = when {
                i == currentStepState.value -> progress.value
                i > currentStepState.value -> 0f
                else -> 1f
            }
            LinearProgressIndicator(
                color = selectedColor,
                backgroundColor = unSelectedColor,
                progress = stepProgress,
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp)
                    .height(5.dp)
            )
        }
    }

    LaunchedEffect(
        isPaused, currentStep
    ) {
        if (isPaused) {
            progress.stop()
        } else {
            for (i in currentStep until stepCount) {
                progress.animateTo(
                    1f,
                    animationSpec = tween(
                        durationMillis = ((1f - progress.value) * stepDuration).toInt(),
                        easing = LinearEasing
                    )
                )
                if (currentStepState.value + 1 <= stepCount - 1) {
                    progress.snapTo(0f)
                    currentStepState.value += 1
                    onStepChanged(currentStepState.value)
                } else {
                    onComplete()
                }
            }
        }
    }

}
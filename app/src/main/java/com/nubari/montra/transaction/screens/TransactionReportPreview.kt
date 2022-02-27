package com.nubari.montra.transaction.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.insets.ui.Scaffold
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.transaction.components.StoryContent
import com.nubari.montra.transaction.components.StoryIndicators
import com.nubari.montra.ui.theme.*
import kotlin.math.max
import kotlin.math.min

@Composable
fun TransactionReportPreview(
    navController: NavController,
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = false
    val stepCount = 4
    val currentStep = remember { mutableStateOf(0) }
    val isPaused = remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = when (currentStep.value) {
            0 -> red100
            1 -> green100
            2 -> violet100
            else -> violet100
        },
        animationSpec = tween(durationMillis = 100, easing = LinearEasing)
    )
    Scaffold {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .systemBarsPadding()
                .padding(10.dp)
        ) {
            val storyModifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { offset ->
                            currentStep.value = if (offset.x < constraints.maxWidth / 2) {
                                max(0, currentStep.value - 1)
                            } else {
                                min(stepCount - 1, currentStep.value + 1)
                            }
                            isPaused.value = false
                        },
                        onPress = {
                            try {
                                isPaused.value = true
                                awaitRelease()
                            } finally {
                                isPaused.value = false
                            }
                        }
                    )
                }
            Column(
                modifier = storyModifier.fillMaxSize()
            ) {
                StoryIndicators(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    stepCount = stepCount,
                    stepDuration = 2_000,
                    unSelectedColor = Color.LightGray,
                    selectedColor = Color.White,
                    currentStep = currentStep.value,
                    onStepChanged = { currentStep.value = it },
                    isPaused = isPaused.value,
                    onComplete = {
                        navController.popBackStack(
                            Destination.TransactionReportPreview.route,
                            true
                        )
                        navController.navigate(
                            Destination.TransactionReport.route
                        )
                    }
                )
                when (currentStep.value) {
                    0 -> {
                        StoryContent(
                            isIncomeExpenseBreakDown = true,
                            isExpense = true,
                            amount = "1,256,637",
                            categoryName = "Travel",
                            categoryAmount = "760,000"
                        )
                    }
                    1 -> {
                        StoryContent(
                            isIncomeExpenseBreakDown = true,
                            isExpense = false,
                            amount = "2,714,050",
                            categoryName = "Salary",
                            categoryAmount = "2,167,023"
                        )
                    }
                    2 -> {
                        StoryContent(
                            isBudgetBreakDown = true,
                            isIncomeExpenseBreakDown = false
                        )
                    }
                    3 -> {
                        StoryContent(
                            isBudgetBreakDown = false,
                            isIncomeExpenseBreakDown = false,
                            isRandomQuote = true,
                            navController = navController
                        )
                    }
                }

            }
        }
    }
}









package com.nubari.montra.transaction.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StoryContent(
    isIncomeExpenseBreakDown: Boolean = true,
    isBudgetBreakDown: Boolean = false,
    isRandomQuote: Boolean = false,
    isExpense: Boolean = false,
    amount: String = "0",
    categoryName: String = "General",
    categoryAmount: String = "0",
    navController: NavController? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = !isRandomQuote) {
            Text(
                text = "This Month",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = Color.White
            )
        }

    }
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        when {
            isIncomeExpenseBreakDown -> {
                IncomeExpenseBreakDown(
                    isExpense = isExpense,
                    amount = amount,
                    categoryName = categoryName,
                    categoryAmount = categoryAmount
                )
            }
            isBudgetBreakDown -> {
                BudgetBreakDown(
                    budgetCount = 12,
                    budgetsExceeded = listOf("Shopping", "Travel", "Drinks")
                )

            }
            isRandomQuote -> {
                RandomQuote(
                    navController = navController
                )
            }
        }

    }
}
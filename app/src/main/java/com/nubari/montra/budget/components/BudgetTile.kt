package com.nubari.montra.budget.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.ui.theme.green100
import com.nubari.montra.ui.theme.light60
import com.nubari.montra.ui.theme.red100
import com.nubari.montra.R
import com.nubari.montra.ui.theme.light20
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@ExperimentalMaterialApi
@Composable
fun BudgetTile(
    budget: Budget,
    modifier: Modifier,
    toDetail: (String) -> Unit
) {
    val budgetRemainder = budget.limit.subtract(budget.spend)
    val budgetSpendPercentage = if (budget.exceeded) {
        100f
    } else {
        budget.spend
            .divide(budget.limit, MathContext(3, RoundingMode.HALF_UP))
            .multiply(BigDecimal.valueOf(100L))
            .toFloat()
    }
    val progress = remember {
        Animatable(0f)
    }
    val progressDestination = budgetSpendPercentage / 100f
    LaunchedEffect(Unit) {
        progress.animateTo(
            progressDestination,
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        )
    }

    Card(
        modifier = modifier.padding(10.dp),
        shape = RoundedCornerShape(10),
        elevation = 10.dp,
        onClick = {
            toDetail(budget.id)
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                color = if (budget.exceeded) {
                                    red100
                                } else {
                                    green100
                                }
                            )
                            .height(20.dp)
                            .width(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = budget.categoryName ?: "General",
                        fontWeight = FontWeight(500),
                        fontSize = 14.sp
                    )
                }
                Box() {
                    if (budget.exceeded) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_warning),
                            contentDescription = "Budget exceeded icon",
                            tint = red100
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (budget.exceeded) {
                        "Remaining ₦0"
                    } else {
                        "Remaining ₦${budgetRemainder.toPlainString()}"
                    },
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold
                )
                LinearProgressIndicator(
                    color = if (budget.exceeded) {
                        red100
                    } else {
                        green100
                    },
                    progress = progress.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .height(10.dp)
                        .clip(RoundedCornerShape(40)),
                    backgroundColor = light60
                )
                Text(
                    text = "₦${budget.spend} of ₦${budget.limit}",
                    fontSize = 16.sp,
                    color = light20
                )
                if (budget.exceeded) {
                    Text(
                        text = "Budget Limit Exceeded",
                        fontSize = 16.sp,
                        color = red100
                    )
                }
            }
        }
    }
}
package com.nubari.montra.transaction.components.transactionreport.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.nubari.montra.R
import com.nubari.montra.general.util.Util
import com.nubari.montra.ui.theme.yellow100
import com.nubari.montra.ui.theme.yellow20

@Composable
fun BudgetBreakDown(
    budgetCount: Int,
    budgetsExceeded: List<String> = emptyList()
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val budgetsExceededCount = budgetsExceeded.size
        Text(
            text = "Looks like you exceeded $budgetsExceededCount of $budgetCount budgets",
            fontWeight = FontWeight(700),
            color = Color.White,
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            mainAxisSpacing = 10.dp,
            mainAxisAlignment = FlowMainAxisAlignment.Center,
            crossAxisAlignment = FlowCrossAxisAlignment.Center,
            modifier = Modifier.fillMaxWidth(),
            crossAxisSpacing = 10.dp
        ) {
            budgetsExceeded.forEach {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40))
                        .background(Color.White)
                        .padding(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20))
                                .background(color = yellow20)
                                .padding(10.dp)

                        ) {
                            Icon(
                                painter = painterResource(
                                    id = Util.iconMap[it] ?: R.drawable.rocket_launch,
                                ),
                                contentDescription = it,
                                tint = yellow100,
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = it,
                            fontWeight = FontWeight(600),
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}
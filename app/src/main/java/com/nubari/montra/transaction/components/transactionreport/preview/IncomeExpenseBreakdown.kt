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
import com.nubari.montra.R
import com.nubari.montra.general.util.Util
import com.nubari.montra.ui.theme.yellow100
import com.nubari.montra.ui.theme.yellow20

@Composable
fun IncomeExpenseBreakDown(
    isExpense: Boolean,
    amount: String,
    categoryName: String,
    categoryAmount: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = if (isExpense) {
                "You Spent \uD83D\uDCB8"
            } else {
                "You Earned \uD83D\uDCB0"
            },
            fontWeight = FontWeight(700),
            fontSize = 32.sp,
            color = Color.White
        )
        Text(
            text = "₦$amount",
            fontWeight = FontWeight(700),
            fontSize = 50.sp,
            color = Color.White
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.5f)
            .clip(
                RoundedCornerShape(5)
            )
            .background(Color.White)
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isExpense) {
                    "Your biggest spending was from"
                } else {
                    "Your biggest income was from"
                },
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(.4f)
                    .height(56.dp),
                contentAlignment = Alignment.Center
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
                                id = Util.iconMap[categoryName] ?: R.drawable.rocket_launch,
                            ),
                            contentDescription = categoryName,
                            tint = yellow100
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = categoryName,
                        fontWeight = FontWeight(600),
                        fontSize = 18.sp
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "₦$categoryAmount",
                fontSize = 30.sp,
                fontWeight = FontWeight(500)
            )
        }
    }
}
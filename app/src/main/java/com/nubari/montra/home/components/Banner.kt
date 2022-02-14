package com.nubari.montra.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubari.montra.R
import com.nubari.montra.ui.theme.*

@Composable
fun HomeBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.3f)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        oldLace,
                        antiqueWhite.copy(
                            alpha = 0.1f
                        )
                    ),
                )
            )
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Account Balance",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = light20
            )
            Text(
                text = "₦9400",
                fontSize = 35.sp,
                fontWeight = FontWeight(600),
                color = dark75
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40))
                        .background(color = green100)
                        .width(180.dp)
                        .height(65.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(
                            start = 10.dp,
                            end = 10.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(25))
                                .background(color = Color.White)
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.income),
                                contentDescription = "income badge",
                                tint = green100
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column() {
                            Text(
                                text = "Income",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight(500)
                            )
                            Text(
                                text = "₦5000",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight(600)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40))
                        .background(color = red100)
                        .width(180.dp)
                        .height(65.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Row(modifier = Modifier.padding(10.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(25))
                                .background(color = Color.White)
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.expense),
                                contentDescription = "expense badge",
                                tint = red100
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column() {
                            Text(
                                text = "Expenses",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight(500)
                            )
                            Text(
                                text = "₦12000",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight(600)
                            )
                        }
                    }
                }

            }
        }
    }
}
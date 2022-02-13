package com.nubari.montra.home.screens

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.PaintingStyle.Companion.Fill
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.R
import com.nubari.montra.general.components.*
import com.nubari.montra.ui.theme.*

val spendingData = listOf<Double>(
    1.0,
    10.0,
    20.0,
    51.0,
    89.0,
    100.8,
    156.0,
    10.0,
    20.0,
    51.0,
    89.0,
    100.8,
)

@Composable
fun Home(
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    MonthDropDown(
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                backgroundColor = oldLace
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.notifiaction),
                        contentDescription = "notifications",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    ) {
        BottomNavigationBarAvoidingBox() {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.35f)
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
                            fontSize = 50.sp,
                            fontWeight = FontWeight(600),
                            color = dark75
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(40))
                                    .background(color = green100)
                                    .weight(1f)
                                    .height(75.dp),
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
                                            .padding(10.dp),
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
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight(500)
                                        )
                                        Text(
                                            text = "₦5000",
                                            color = Color.White,
                                            fontSize = 24.sp,
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
                                    .weight(1f)
                                    .height(75.dp),
                                contentAlignment = Alignment.Center

                            ) {
                                Row(modifier = Modifier.padding(10.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(25))
                                            .background(color = Color.White)
                                            .padding(10.dp),
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
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight(500)
                                        )
                                        Text(
                                            text = "₦12000",
                                            color = Color.White,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight(600)
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Spend Frequency",
                        fontSize = 18.sp,
                        fontWeight = FontWeight(600),
                        modifier = Modifier.padding(
                            top=10.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    ) {
                        val dummyData = listOf<Double>(
                            700.0,
                            450.0,
                            1400.0,
                            0.0,
                            600.0,
                        )
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)

                        ) {

                            drawPath(
                                path = generateSpendingLineGraphStrokePath(
                                    size = size,
                                    spendingData = dummyData
                                ),
                                color = violet100,
                                style = Stroke(15f)
                            )
                            drawPath(
                                path = generateSpendingLineGraphFillPath(
                                    size = size,
                                    spendingData = dummyData
                                ),
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        violet.copy(
                                            alpha = 0.40f
                                        ),
                                        violet.copy(
                                            alpha = 0.01f
                                        )
                                    ),
                                )
                            )


                        }
                    }


                }
            }

        }
    }


}

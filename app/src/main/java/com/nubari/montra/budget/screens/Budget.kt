package com.nubari.montra.budget.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import com.nubari.montra.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.budget.components.BudgetSection
import com.nubari.montra.budget.components.NoBudgetSection
import com.nubari.montra.budget.events.BudgetsEvent
import com.nubari.montra.budget.viewmodels.BudgetViewModel
import com.nubari.montra.general.util.Util.months
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.ui.theme.violet100


@ExperimentalMaterialApi
@Composable
fun Budget(
    navController: NavController,
    budgetsViewModel: BudgetViewModel = hiltViewModel()
) {
    val state = budgetsViewModel.state.value
    val monthIndex = state.currentMonth

    val incrementIndex = {
        if (monthIndex < months.size - 1) {
            val newMonth = monthIndex + 1
            budgetsViewModel.createEvent(
                BudgetsEvent.ChangeMonth(
                    newMonth = newMonth
                )
            )
        }
    }
    val decrementIndex = {
        if (monthIndex > 0) {
            val newMonth = monthIndex - 1
            budgetsViewModel.createEvent(
                BudgetsEvent.ChangeMonth(
                    newMonth = newMonth
                )
            )

        }
    }

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .background(violet100)
                .padding(top = 20.dp)
                .fillMaxSize()

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.2f)
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    decrementIndex()
                }) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_chevron_left
                        ),
                        contentDescription = "Previous Month",
                        tint = Color.White,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    )
                }
                Text(
                    text = months[monthIndex],
                    color = Color.White,
                    fontSize = 28.sp,
                )
                IconButton(onClick = {
                    incrementIndex()
                }) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_chevron_right_
                        ),
                        contentDescription = "Next Month",
                        tint = Color.White,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 35.dp,
                            topEnd = 35.dp
                        )
                    )
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 10.dp,
                            end = 10.dp
                        ),
                ) {
                    if (state.monthsBudgets.isEmpty()) {
                        NoBudgetSection()
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.8f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BudgetSection(
                                budgetList = state.monthsBudgets,
                                toDetailFunc = { id ->
                                    Log.i("yyy", "detail id $id")
                                    val route = Destination.BudgetDetail.route +
                                            "?bdId=${id}"
                                    Log.i("yyy", route)
                                    navController.navigate(route = route)
                                }
                            )
                        }
                    }
                    Button(
                        onClick = {
                            navController.navigate(
                                route = Destination.BudgetForm.route
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White,
                        ),
                        shape = RoundedCornerShape(30),
                    ) {
                        Text(text = "Create a budget")
                    }

                }

            }
        }
    }


}




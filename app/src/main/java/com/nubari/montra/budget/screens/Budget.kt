package com.nubari.montra.budget.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.Scaffold
import com.nubari.montra.budget.components.BudgetSection
import com.nubari.montra.budget.components.NoBudgetSection
import com.nubari.montra.budget.viewmodels.BudgetViewModel
import com.nubari.montra.general.components.app.MainAppBar
import com.nubari.montra.navigation.destinations.Destination
import com.nubari.montra.ui.theme.violet100


@ExperimentalMaterialApi
@Composable
fun Budget(
    navController: NavController,
    budgetsViewModel: BudgetViewModel = hiltViewModel()
) {
    val state = budgetsViewModel.state.value


    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MainAppBar(
                navController = navController,
                title = {
                    Text(
                        text = "All Budgets",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                backIconColor = Color.Black,
                backgroundColor = violet100
            )
        }
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
                    if (state.budgets.isEmpty()) {
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
                                budgetList = state.budgets,
                                toDetailFunc = { id ->
                                    val route = Destination.BudgetDetail.route +
                                            "?bdId=${id}"
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



